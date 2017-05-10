package com.neo.duan.net;

import com.neo.duan.net.handler.IHttpHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: neo.duan
 * Date: 2017/05/10 14:10
 * Desc: retrofit返回回调
 */
public class DefaultCallback implements Callback<String> {

    private IHttpHandler handler;

    public DefaultCallback(IHttpHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response == null) {
            onFailure(call, new Throwable());
        } else {
            handler.onResponse(response.body());
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        handler.onFailure(t);
    }
}
