package com.neo.duan.net.handler;


import com.neo.duan.net.listener.IHttpListener;
import com.neo.duan.net.request.IBaseRequest;
import com.neo.duan.net.response.IServerResponse;
import com.neo.duan.utils.JSONUtils;
import com.neo.duan.utils.LogUtils;


/**
 * Author: neo.duan
 * Date: 2017/02/20 10:41
 * Desc: 公共http分发器
 */
public class DefaultHttpHandler<T extends IServerResponse> extends BaseHttpHandler<T>
        implements IHttpHandler<T> {

    public DefaultHttpHandler(IBaseRequest request, IHttpListener listener) {
        super(request, listener);
    }

    @Override
    public void onResponse(String response) {
        super.onResponse(response);
        //转换为服务器返回的对象
        IServerResponse serverResp = JSONUtils.parseObject(response, getServerResponseClazz());

        LogUtils.d(TAG, "BaseHttpHandler  onResponse---->" + serverResp.toString());
        if (!serverResp.isSuccess()) {
            if (mListener != null) {
                mListener.onResponse(IHttpListener.RESPONSE_FAIL, "服务器开了点小差，请稍后再试",
                        serverResp.getStatus());
            }
            return;
        }

        //过关：回调成功
        if (mListener != null) {
            mListener.onResponse(IHttpListener.RESPONSE_SUCCESS, serverResp,
                    serverResp.getStatus());
        }
    }

    @Override
    public void onFailure(Throwable t) {
        super.onFailure(t);
    }
}
