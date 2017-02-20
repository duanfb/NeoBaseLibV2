package com.neo.duan.net.handler;


import com.neo.duan.net.listener.IHttpListener;
import com.neo.duan.net.request.IBaseRequest;

/**
 * Author: neo.duan
 * Date: 2017/02/20 10:41
 * Desc: 公共http分发器
 */
public class HttpHandler<T> extends BaseHttpHandler<T> {
    public HttpHandler(IBaseRequest request, IHttpListener listener,
                       int tag) {
        super(request, listener, tag);
    }
}
