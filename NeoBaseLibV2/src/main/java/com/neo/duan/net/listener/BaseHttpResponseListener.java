
package com.neo.duan.net.listener;


import com.neo.duan.mvp.view.base.IBaseView;
import com.neo.duan.utils.LogUtils;

import java.lang.ref.WeakReference;

/**
 * Author: neo.duan
 * Date: 2017/02/20 17:06
 * Desc: 网络请求回调：使用静态内部类，防止持有外部类引用
 */
public class BaseHttpResponseListener implements IHttpListener {
    protected final String TAG = getClass().getSimpleName();

    private WeakReference<IBaseView> reference;

    public BaseHttpResponseListener(IBaseView baseView) {
        reference = new WeakReference<>(baseView);
    }

    @Override
    public void onResponse(int code, Object jsonObject, String status) {
        switch (code) {
            case RESPONSE_START: //请求开始
                onStart();
                break;
            case RESPONSE_SUCCESS://请求成功且服务器成返回
                onSuccess(jsonObject);
                break;
            case RESPONSE_FAIL://请求失败：服务器返回错误或者超时，弹出提示
                if (jsonObject == null) {
                    onFail(status, "服务器开了点小差，请稍后再试");
                } else {
                    onFail(status, jsonObject.toString());
                }
                break;
            case RESPONSE_CANCEL://请求已取消
                onCancel(jsonObject == null ? "" : jsonObject.toString());
                break;
            case RESPONSE_DONE://请求完成：数据异常，弹出提示
                onDone(jsonObject == null ? "" : jsonObject.toString());
                break;
            case RESPONSE_ERROR://网络错误：无网络
                onError();
                break;
        }
    }

    public void onStart() {
        IBaseView baseView = reference.get();
        if (baseView != null) {
            baseView.showLoading();
        }
    }

    public void onSuccess(Object model) {
        IBaseView baseView = reference.get();
        if (baseView != null) {
            baseView.hideLoading();
        }
    }

    public void onFail(String status, String msg) {
        LogUtils.e(TAG, msg);
        IBaseView baseView = reference.get();
        if (baseView != null) {
            baseView.showErrorMsg(msg);
            baseView.hideLoading();
        }
    }

    public void onCancel(String msg) {
        LogUtils.e(TAG, msg);
        IBaseView baseView = reference.get();
        if (baseView != null) {
            baseView.hideLoading();
        }
    }

    public void onDone(String msg) {
        IBaseView baseView = reference.get();
        if (baseView != null) {
            baseView.showErrorMsg(msg);
            baseView.hideLoading();
        }
    }

    public void onError() {
        IBaseView baseView = reference.get();
        if (baseView != null) {
            baseView.hideLoading();
            baseView.showNetError();
            baseView.showErrorMsg("网络连接不畅，请检查一下您的网络！");
        }
    }
}
