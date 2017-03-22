package com.neo.duan.net.handler;

import android.text.TextUtils;

import com.neo.duan.net.listener.BaseHttpResponseListener;
import com.neo.duan.net.listener.IHttpListener;
import com.neo.duan.net.request.IBaseRequest;
import com.neo.duan.net.response.BaseResponse;
import com.neo.duan.net.response.ServerResponse;
import com.neo.duan.utils.JSONUtils;
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
public class BaseHttpHandler<T> implements Callback<ServerResponse> {
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
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        //第一关卡
        if (response == null) {
            if (mListener != null) {
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL, "请求失败，请重试", mTag);
            }
            return;
        }

        //第二关卡
        ServerResponse serverResp = response.body();
        if (serverResp == null) {
            if (mListener != null) {
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL, "请求失败，请重试", mTag);
            }
            return;
        }

        //第三关卡
        LogUtils.d(TAG, "BaseHttpHandler  onResponse---->" + serverResp.toString());
        if (!serverResp.isSuccess()) {
            if (mListener != null) {
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL, "服务器开了点小差，请稍后再试", mTag);
            }
            return;
        }

        //第四关卡
        String serverRespParams = serverResp.getParams();
        if (TextUtils.isEmpty(serverRespParams)) {
            if (mListener != null) {
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL, "服务器开了点小差，请稍后再试", mTag);
            }
            return;
        }

        //第五关卡:先用BaseResponse封装，判断是否成功
        BaseResponse resp = JSONUtils.parseObject(serverRespParams, BaseResponse.class);
        if (resp == null) {
            if (mListener != null) {
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL, "服务器开了点小差，请稍后再试", mTag);
            }
            return;
        }

        //第六关卡
        if (!resp.isSuccess()) {
            if (mListener != null) {
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL, resp, mTag);
            }
            return;
        }

        //第九关:再将serverRespParams序列化成Model进行分发
        Object infoResp = JSONUtils.parseObject(serverRespParams, getResponseClazz());

        //过关：回调成功
        if (mListener != null) {
            mListener.onResponse(BaseHttpResponseListener.RESPONSE_SUCCESS, infoResp, mTag);
        }
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
    public void onFailure(Call<ServerResponse> call, Throwable t) {
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

    public Class<?> getResponseClazz() {
        return mRequest.getResponseClazz();
    }
}
