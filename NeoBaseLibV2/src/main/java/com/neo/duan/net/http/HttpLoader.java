package com.neo.duan.net.http;


import com.neo.duan.AppBaseApplication;
import com.neo.duan.net.handler.BaseHttpHandler;
import com.neo.duan.net.request.ProgressRequestBody;
import com.neo.duan.net.request.base.BaseRequest;
import com.neo.duan.net.request.base.FileUploadReq;
import com.neo.duan.net.response.BaseResponse;
import com.neo.duan.utils.NetWorkUtils;
import com.neo.duan.utils.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author : neo.duan
 * @date : 	 2016/7/26 0026
 * @desc : 请描述该文件
 */
public class HttpLoader extends BaseHttpLoader {
    private static final int MAX_CALL_COUNT = 8; //最大请求数
    private CallCache mCallCache;

    private static class HttpLoaderHolder {
        private static final HttpLoader instance = new HttpLoader();
    }

    private HttpLoader() {
        super();
        mCallCache = new CallCache();
    }

    public static final HttpLoader getInstance() {
        return HttpLoaderHolder.instance;
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

        BaseRequest request = handler.getRequest();

        //校验请求缓存中是否有该请求，有则取消
//        Call oldCall = mCallCache.get(request);
//        //去拦截器校验该请求是否需要取消
//        if (oldCall != null && !UnCancelInterceptor.requests.contains(request.getClass())) {
//            oldCall.cancel();
//            mCallCache.remove(request);
//        }

        Map<String, Object> params = request.getParams();

        Call<BaseResponse> newCall = mApiService.sendPost(params, request.getApi());

        //处理回调
        newCall.enqueue(handler);

        //将请求添加到集合中，方便取消请求,缓存中只缓存5个请求,也就说最多允许同时发5个请求
//        if (mCallCache.size() > MAX_CALL_COUNT) {
//            LogUtils.e(TAG, "call count size more " + MAX_CALL_COUNT + ", will cancel the index of 0 call");
//            //取消最后一个请求
//            Call lastCall = mCallCache.getLast();
//            if (lastCall != null && !lastCall.isCanceled()) {
//                lastCall.cancel();
//                mCallCache.removeLast();
//            }
//        } else {
//            mCallCache.add(request, newCall);
//        }
    }

    /**
     * 上传文件 https://futurestud.io/tutorials/retrofit-2-how-to-upload-files-to-server
     *
     * @param handler
     */
    public void upload(BaseHttpHandler handler, ProgressRequestBody.UploadCallbacks callbacks) {
        checkHandler(handler);
        //通过校验，调用请求开始
        handler.onStart();

        //校验网络是否正常
        if (!NetWorkUtils.isAvailable(AppBaseApplication.getInstance())) {
            handler.onNetWorkErrorResponse();
            //TODO 根据产品需求要不要获取缓存数据
            return;
        }

        FileUploadReq request = (FileUploadReq) handler.getRequest();

        Map<String, RequestBody> paramMap = new HashMap<>();
        //请求添加参数
        Iterator iterator = request.getParams().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            paramMap.put(entry.getKey().toString(), toRequestBody(entry.getValue().toString()));
        }


        //以下构造上传文件的请求参数体
        File file = new File(request.getFilePath());

        ProgressRequestBody fileBody = new ProgressRequestBody(file, callbacks);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("multipart/form-data", file.getName(), fileBody);

        //将请求参数和请求体发送执行
        Call<BaseResponse> newCall = mApiService.upload(paramMap, body, request.getApi());

        //处理回调
        newCall.enqueue(handler);
    }

    public void checkHandler(BaseHttpHandler handler) {
        //校验handler处理对象是否为空，抛异常，调用者处理
        if (handler == null) {
            throw new IllegalArgumentException("http handler object is null");
        }

        //校验请求对象是否为空
        BaseRequest request = handler.getRequest();
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
    public void cancel(BaseRequest req) {
        //取消缓存中请求
        Call call = mCallCache.get(req);
        if (call != null && !call.isCanceled()) {
            call.cancel();
            mCallCache.remove(req);
        }
    }

    public void cancelAll() {
        for (int i = 0; i < mCallCache.size(); i++) {
            Call call = mCallCache.get(i);
            if (call != null && !call.isCanceled()) {
                call.cancel();
            }
        }
        mCallCache.clear();
    }

    public static RequestBody toRequestBody (String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value) ;
    }
}
