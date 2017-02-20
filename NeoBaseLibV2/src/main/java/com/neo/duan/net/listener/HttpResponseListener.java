package com.neo.duan.net.listener;

import com.neo.duan.mvp.view.base.IBaseView;
import com.neo.duan.utils.LogUtils;

import java.lang.ref.WeakReference;

/**
 * Author: neo.duan
 * Date: 2017/02/20 10:41
 * Desc: 使用静态内部类，防止持有外部类引用
 */
public class HttpResponseListener extends BaseHttpResponseListener {
    private WeakReference<IBaseView> reference;

    public HttpResponseListener(IBaseView baseView) {
        reference = new WeakReference<>(baseView);
    }

    @Override
    public void onStart() {
        IBaseView baseView = reference.get();
        if (baseView != null) {
            baseView.showLoading();
        }
    }

    @Override
    public void onSuccess(Object model) {
        IBaseView baseView = reference.get();
        if (baseView != null) {
            baseView.hideLoading();
        }
    }

    @Override
    public void onFail(String msg) {
        LogUtils.e(TAG, msg);
        IBaseView baseView = reference.get();
        if (baseView != null) {
            baseView.showErrorMsg(msg);
            baseView.hideLoading();
        }
    }

    @Override
    public void onCancel(String msg) {
        LogUtils.e(TAG, msg);
        IBaseView baseView = reference.get();
        if (baseView != null) {
            baseView.hideLoading();
        }
    }

    @Override
    public void onDone(String msg) {
        IBaseView baseView = reference.get();
        if (baseView != null) {
            baseView.showErrorMsg(msg);
            baseView.hideLoading();
        }
    }

    @Override
    public void onError() {
        IBaseView baseView = reference.get();
        if (baseView != null) {
            baseView.hideLoading();
            baseView.showNetError();
        }
    }
}
