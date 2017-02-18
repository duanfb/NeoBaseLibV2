package com.neo.duan.net.handler;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.neo.duan.BuildConfig;
import com.neo.duan.entity.base.BaseInfo;
import com.neo.duan.net.listener.BaseHttpResponseListener;
import com.neo.duan.net.listener.IHttpListener;
import com.neo.duan.net.request.base.BaseRequest;
import com.neo.duan.net.response.BaseResponse;
import com.neo.duan.utils.DESUtils;
import com.neo.duan.utils.LogUtils;
import com.neo.duan.utils.constants.Constants;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author : neo.duan
 * @date : 	 2016/9/20
 * @desc : 请求和返回处理器
 */
public class BaseHttpHandler<T> implements Callback<BaseResponse> {
    private static final String TAG = "BaseHttpHandler";
    private BaseRequest mRequest;
    private IHttpListener mListener;
    private int mTag;
    public BaseHttpHandler(BaseRequest request, IHttpListener listener, int tag) {
        this.mRequest = request;
        this.mListener = listener;
        this.mTag = tag;
    }

    /**
     * 请求前
     */
    public void onStart() {
        LogUtils.d(TAG,"BaseHttpHandler  onRequest---->" + getRequest().getApi());
        Map<String, Object> params = mRequest.getParams();
        LogUtils.d(TAG,params);
        if (mListener != null) {
            mListener.onResponse(BaseHttpResponseListener.RESPONSE_START, mRequest, mTag);
        }
    }


    @Override
    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
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
        BaseResponse resp = response.body();
        if (resp == null) {
            if (mListener != null) {
                detailErrorMsg = BuildConfig.LOG_DEBUG ? "  服务器返回空" : "";
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL,
                        "请求失败，请重试" + detailErrorMsg, mTag);
            }
            return;
        }

        LogUtils.d(TAG,"BaseHttpHandler  onResponse---->" + response.body().toString());
        //TODO 目前不过第三关，正式打开
        //第三关:Nonce是否为request中Nonce
//        if (!mRequest.getNonce().equals(resp.getNonce())) {
//            if (mListener != null) {
//                detailErrorMsg = BuildConfig.LOG_DEBUG ? "  Nonce不匹配" : "";
//                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL,
//                        "服务器开了点小差，请稍后再试" + detailErrorMsg, mTag);
//            }
//            return;
//        }

        //第四关：Result是否为成功
        if (!resp.isSuccess()) {
            if (mListener != null) {
                String msg = "服务器开了点小差，请稍后再试";
                String errorMessage = resp.getErrorMessage();
                if (!TextUtils.isEmpty(errorMessage)) {
                    msg = errorMessage;
                }
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL, msg, mTag);
            }
            return;
        }

        //第五关：ResponseData是否为空:如果为空，分发message
        Object obj = resp.getData();
        if (obj == null) {
            if (mListener != null) {
//                detailErrorMsg = BuildConfig.LOG_DEBUG ? "  ResponseData为空" : "";
//                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL,
//                        "请求失败，请重试" + detailErrorMsg, mTag);
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_SUCCESS, resp.getMessage(), mTag);
            }
            return;
        }

        //第六关：序列化的BaseIfo是否为空
        String responseData = obj.toString();
        //解密该数据
        try {
            responseData = DESUtils.decrypt(Constants.WEIYI_KEY, responseData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogUtils.d(TAG,"DES decrypt:" + responseData);

        BaseInfo baseInfo = JSON.parseObject(responseData, mRequest.getResponseClazz());
        if (baseInfo == null) {
            if (mListener != null) {
                detailErrorMsg = BuildConfig.LOG_DEBUG ? "  BaseInfo序列化为空" : "";
                mListener.onResponse(BaseHttpResponseListener.RESPONSE_FAIL,
                        "请求失败，请重试" + detailErrorMsg, mTag);
            }
            return;
        }


        //过关：回调成功
        if (mListener != null) {
            mListener.onResponse(BaseHttpResponseListener.RESPONSE_SUCCESS, baseInfo, mTag);
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
    public void onFailure(Call<BaseResponse> call, Throwable t) {
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

    public BaseRequest getRequest() {
        return mRequest;
    }
}
