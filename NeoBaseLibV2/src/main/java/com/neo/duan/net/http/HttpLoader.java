package com.neo.duan.net.http;


import com.neo.duan.AppBaseApplication;
import com.neo.duan.net.HttpLoaderConfiguration;
import com.neo.duan.net.handler.BaseHttpHandler;
import com.neo.duan.net.request.IBaseRequest;
import com.neo.duan.utils.LogUtils;
import com.neo.duan.utils.NetWorkUtils;
import com.neo.duan.utils.StringUtils;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Author: neo.duan
 * Date: 2017/02/20
 * Desc: 公共请求类
 */
public class HttpLoader extends BaseHttpLoader {
    private static final int MAX_CALL_COUNT = 8; //最大请求数
    private HttpLoaderConfiguration configuration;
    private RequestCache mRequestCache;

    private static class HttpLoaderHolder {
        private static final HttpLoader instance = new HttpLoader();
    }

    private HttpLoader() {
        super();
        mRequestCache = new RequestCache();
    }

    public static HttpLoader getInstance() {
        return HttpLoaderHolder.instance;
    }

    public void init(HttpLoaderConfiguration configuration) {
        if(configuration == null) {
            throw new IllegalArgumentException("ImageLoader configuration can not be initialized with null");
        } else {
            if(this.configuration == null) {
                LogUtils.d(TAG, "Initialize HttpLoader with configuration");
                this.configuration = configuration;
                super.initBase(configuration);
            } else {
                LogUtils.w(TAG, "Try to initialize HttpLoader which had already been " +
                        "initialized before.");
            }
        }
    }

    /**
     * 发送请求
     *
     * @param handler
     */
    public void sendRequest(BaseHttpHandler handler) {
        checkHandler(handler);
        //通过校验，调用请求开始
        handler.onStart();

        //校验网络是否正常
        if (!NetWorkUtils.isAvailable(AppBaseApplication.getInstance())) {
            handler.onNetWorkErrorResponse();
            //TODO 根据产品需求要不要获取缓存数据
            return;
        }

        IBaseRequest request = handler.getRequest();

        //发送请求前，校验请求缓存池
        if (mRequestCache.size() > MAX_CALL_COUNT) {
            LogUtils.e(TAG, "request count size more " + MAX_CALL_COUNT + ", will cancel the index of last call");
            //取消最后一个请求
            IBaseRequest lastReq = mRequestCache.getLast();
            if (lastReq != null && !lastReq.isCanceled()) {
                lastReq.cancel();
                mRequestCache.removeLast();
            }
        } else {
            mRequestCache.add(request);
        }

        //发送请求
        mApiService.sendPost(request.getParams(), request.getApi()).enqueue(handler);
    }

    private void checkHandler(BaseHttpHandler handler) {
        //校验handler处理对象是否为空，抛异常，调用者处理
        if (handler == null) {
            throw new IllegalArgumentException("http handler object is null");
        }

        //校验请求对象是否为空
        IBaseRequest request = handler.getRequest();
        if (request == null) {
            throw new IllegalArgumentException("http handler the request object is null");
        }

        String api = request.getApi();
        //校验url是否为空
        if (StringUtils.isBlank(api)) {
            throw new IllegalArgumentException("http handler the request api is null");
        }
    }

    /**
     * 取消请求
     *
     * @param req
     */
    public void cancel(IBaseRequest req) {
        //取消缓存中请求
        IBaseRequest oldReq = mRequestCache.get(req);
        if (oldReq != null && !oldReq.isCanceled()) {
            oldReq.cancel();
            mRequestCache.remove(req);
        }
    }

    public static RequestBody toRequestBody (String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value) ;
    }
}
