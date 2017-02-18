package com.neo.duan.net.handler;


import com.neo.duan.net.listener.IHttpListener;
import com.neo.duan.net.request.base.BaseRequest;

/**
 * @author : neo.duan
 * @date : 	 2016/7/26 0026
 * @desc : 公共http分发器
 */
public class HttpHandler<T> extends BaseHttpHandler<T> {
    public HttpHandler(BaseRequest request, IHttpListener listener,
                       int tag) {
        super(request, listener, tag);
    }
}
