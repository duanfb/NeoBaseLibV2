package com.neo.duan.net.handler;

import android.text.TextUtils;

import com.neo.duan.net.listener.IHttpListener;
import com.neo.duan.net.request.IBaseRequest;
import com.neo.duan.net.response.IServerResponse;
import com.neo.duan.utils.LogUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;


/**
 * Author: neo.duan
 * Date: 2017/02/20 10:41
 * Desc: 请求和返回处理器
 */
public class BaseHttpHandler<T extends IServerResponse> implements IHttpHandler<T> {
    public static final String TAG = BaseHttpHandler.class.getSimpleName();
    public IBaseRequest mRequest;
    public IHttpListener mListener;

    public BaseHttpHandler(IBaseRequest request, IHttpListener listener) {
        this.mRequest = request;
        this.mListener = listener;
    }

    /**
     * 请求前
     */
    public void onStart() {
        LogUtils.d(TAG, "BaseHttpHandler  onRequest---->" + getRequest().getApi());
        Map<String, Object> params = mRequest.getParams();
        LogUtils.d(TAG, params);
        if (mListener != null) {
            mListener.onResponse(IHttpListener.RESPONSE_START, mRequest, IHttpListener.STATUS_UNKNOWN);
        }
    }


    @Override
    public void onResponse(String response) {
        //校验请求是否已经标识为取消
        if (mRequest != null && mRequest.isCanceled()) {
            if (mListener != null) {
                mListener.onResponse(IHttpListener.RESPONSE_CANCEL, "请求已取消", IHttpListener.STATUS_UNKNOWN);
            }
            return;
        }

        //第一关卡
        if (TextUtils.isEmpty(response)) {
            if (mListener != null) {
                mListener.onResponse(IHttpListener.RESPONSE_FAIL, "请求失败，请重试", IHttpListener.STATUS_UNKNOWN);
            }
            return;
        }
    }

    /**
     * 网络异常
     */
    public void onNetWorkErrorResponse() {
        if (mListener != null) {
            mListener.onResponse(IHttpListener.RESPONSE_ERROR,
                    "网络连接不畅，请检查一下您的网络！", IHttpListener.STATUS_UNKNOWN);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (t == null) {
            if (mListener != null) {
                mListener.onResponse(IHttpListener.RESPONSE_FAIL, "请求失败，请重试", IHttpListener.STATUS_UNKNOWN);
            }
            return;
        }
        LogUtils.e(TAG, t.getMessage());

        //请求取消
        if (t instanceof IOException && "Canceled".equals(t.getMessage())) {
            if (mListener != null) {
                mListener.onResponse(IHttpListener.RESPONSE_CANCEL, "请求已取消", IHttpListener.STATUS_UNKNOWN);
            }
            return;
        }

        //请求超时
        if (t instanceof SocketTimeoutException) {
            if (mListener != null) {
                mListener.onResponse(IHttpListener.RESPONSE_DONE, "请求超时，请检查网络连接", IHttpListener.STATUS_UNKNOWN);
            }
        }

        //其他情况统一为失败
        if (mListener != null) {
            mListener.onResponse(IHttpListener.RESPONSE_FAIL, "请求失败，请重试", IHttpListener.STATUS_UNKNOWN);
        }
    }

    public IBaseRequest getRequest() {
        return mRequest;
    }

    public Class<? extends IServerResponse> getServerResponseClazz() {
        return mRequest.getServerResponseClazz();
    }

    public Class<?> getResponseClazz() {
        return mRequest.getResponseClazz();
    }
}
