
package com.neo.duan.net.listener;


/**
 * 网络请求回调
 * @author neo.duan
 *
 */
public abstract class BaseHttpResponseListener implements IHttpListener {
    protected final String TAG = getClass().getSimpleName();

    @Override
    public void onResponse(int code, Object jsonObject, int tag) {
        switch (code) {
            case RESPONSE_START: //请求开始
                onStart();
                break;
            case RESPONSE_SUCCESS://请求成功且服务器成返回
                onSuccess(jsonObject);
                break;
            case RESPONSE_FAIL://请求失败：服务器返回错误或者超时，弹出提示
                onFail(jsonObject == null ? "" : jsonObject.toString());
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

    public abstract void onStart();
    public abstract void onSuccess(Object model);
    public abstract void onFail(String msg);
    public abstract void onCancel(String msg);
    public abstract void onDone(String msg);
    public abstract void onError();
}
