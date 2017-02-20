package com.neo.duan.net.handler;

import com.neo.duan.BuildConfig;
import com.neo.duan.net.listener.BaseHttpResponseListener;
import com.neo.duan.net.listener.IHttpListener;
import com.neo.duan.net.request.IBaseRequest;
import com.neo.duan.net.response.IBaseResponse;
import com.neo.duan.utils.LogUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Author: neo.duan
 * Date: 2017/02/20 10:41
 * Desc: 请求和返回处理器
 */
public class BaseHttpHandler<T> implements Callback<IBaseResponse> {
    private static final String TAG = BaseHttpHandler.class.getSimpleName();
    private IBaseRequest mRequest;
    private IHttpListener mListener;
    private int mTag;

    public BaseHttpHandler(IBaseRequest request, IHttpListener listener, int tag) {
        this.mRequest = request;
        this.mListener = listener;
        this.mTag = tag;
    }

    /**
     * 请求前
     */
    public void onStart() {
        LogUtils.d(TAG, "BaseHttpHandler  onRequest---->" + getRequest().getApi());
        Map<String, Object> params = mRequest.getParams();
        LogUtils.d(TAG, params);
        if (mListener != null) {
            mListener.onResponse(BaseHttpResponseListener.RESPONSE_START, mRequest, mTag);
        }
    }


    @Override
    public void onResponse(Call<IBaseResponse> call, Response<IBaseResponse> response) {
        String detailErrorMsg;

        //第一关
        if (response == null) {
            if (mListener != null) {
                detailErrorMsg = BuildConfig.LOG_DEBUG ? "  服务器返回空" : "";
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL,
                        "请求失败，请重试" + detailErrorMsg, mTag);
            }
            return;
        }

        //第二关
        IBaseResponse resp = response.body();
        if (resp == null) {
            if (mListener != null) {
                detailErrorMsg = BuildConfig.LOG_DEBUG ? "  服务器返回空" : "";
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL,
                        "请求失败，请重试" + detailErrorMsg, mTag);
            }
            return;
        }

        LogUtils.d(TAG, "BaseHttpHandler  onResponse---->" + response.body().toString());

        //第四关：Result是否为成功
//        if (!resp.isSuccess()) {
//            if (mListener != null) {
//                String msg = "服务器开了点小差，请稍后再试";
//                String errorMessage = resp.getErrorMessage();
//                if (!TextUtils.isEmpty(errorMessage)) {
//                    msg = errorMessage;
//                }
//                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL, msg, mTag);
//            }
//            return;
//        }
    }

    /**
     * 网络异常
     */
    public void onNetWorkErrorResponse() {
        if (mListener != null) {
            mListener.onResponse(BaseHttpResponseListener.RESPONSE_ERROR,
                    "网络连接不畅，请检查一下您的网络！", mTag);
        }
    }

    @Override
    public void onFailure(Call<IBaseResponse> call, Throwable t) {
        if (t == null) {
            if (mListener != null) {
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL, "请求失败，请重试", mTag);
            }
            return;
        }
        LogUtils.e(TAG, t.getMessage());

        //请求取消
        if (t instanceof IOException && "Canceled".equals(t.getMessage())) {
            if (mListener != null) {
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_CANCEL, "请求已取消", mTag);
            }
            return;
        }

        //请求超时
        if (t instanceof SocketTimeoutException) {
            if (mListener != null) {
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_DONE, "请求超时，请检查网络连接", mTag);
            }
        }

        //其他情况统一为失败
        if (mListener != null) {
            mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL, "请求失败，请重试", mTag);
        }
    }

    public IBaseRequest getRequest() {
        return mRequest;
    }
}
