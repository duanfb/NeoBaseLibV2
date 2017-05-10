package com.neo.duan.net.handler;

import com.neo.duan.net.response.IServerResponse;



/**
 * Author: neo.duan
 * Date: 2017/05/10 13:52
 * Desc: 处理器接口
 */
public interface IHttpHandler<T extends IServerResponse> {

    /**
     * 请求开始
     */
    void onStart();

    /**
     * 网络错误
     */
    void onNetWorkErrorResponse();

    /**
     * http请求返回
     * @param response response
     */
    void onResponse(String response);

    /**
     * http请求失败返回
     * @param t Throwable
     */
    void onFailure(Throwable t);
}
