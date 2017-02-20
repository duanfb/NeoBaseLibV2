package com.neo.duan.mvp.interactor;

import com.neo.duan.net.handler.HttpHandler;
import com.neo.duan.net.http.HttpLoader;
import com.neo.duan.net.listener.IHttpListener;
import com.neo.duan.net.request.IBaseRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: neo.duan
 * Date: 2017/02/20 10:41
 * Desc:
 */
public class BaseInteractorImpl implements BaseInteractor{
    private final List<IBaseRequest> mReqList = new ArrayList<>();

    public void sendRequest(IBaseRequest request, IHttpListener listener) {
        HttpHandler handler = new HttpHandler(request,listener,0);
        mReqList.add(handler.getRequest());
        HttpLoader.getInstance().sendRequest(handler);
    }

    @Override
    public void onDestroy() {
        for (IBaseRequest req : mReqList) {
            HttpLoader.getInstance().cancel(req);
        }
    }
}
