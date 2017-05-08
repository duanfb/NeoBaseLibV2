package com.neo.duan.net.http;


import com.neo.duan.net.request.IBaseRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author : neo.duan
 * @date : 	 2016/10/11
 * @desc : 管理有关请求的Call的缓存处理
 */
public class RequestCache {
    private List<IBaseRequest> reqCache = new ArrayList<>();

    public RequestCache() {
        Collections.synchronizedList(reqCache);
    }

    public void add(IBaseRequest request) {
        reqCache.add(0, request);
    }

    /**
     * 获取该请求对应的Call
     *
     * @param request
     * @return
     */
    public IBaseRequest get(IBaseRequest request) {
        if (request == null) {
            return null;
        }
        Iterator<IBaseRequest> iterator = reqCache.iterator();
        while (iterator.hasNext()) {
            IBaseRequest req = iterator.next();
            if (request.getApi().equals(req.getApi())) {
                return req;
            }
        }
        return null;
    }

    /**
     * 获取对应的Call
     *
     * @param index
     * @return
     */
    public IBaseRequest get(int index) {
        if (index < 0) {
            return null;
        }
        return reqCache.get(index);
    }

    /**
     * 获取末尾对应的Call
     *
     * @return
     */
    public IBaseRequest getLast() {
        return get(reqCache.size() - 1);
    }

    /**
     * 集合中是否缓存该请求
     *
     * @param request
     * @return
     */
    public boolean contails(IBaseRequest request) {
        if (request == null) {
            return false;
        }

        Iterator<IBaseRequest> iterator = reqCache.iterator();
        while (iterator.hasNext()) {
            IBaseRequest req = iterator.next();
            if (request.getApi().equals(req.getApi())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除
     *
     * @param request
     * @return
     */
    public void remove(IBaseRequest request) {
        if (request == null) {
            return;
        }
        Iterator<IBaseRequest> iterator = reqCache.iterator();
        while (iterator.hasNext()) {
            IBaseRequest req = iterator.next();
            if (request.getApi().equals(req.getApi())) {
                iterator.remove();
            }
        }
    }

    /**
     * 删除对应位置数据
     *
     * @param index
     */
    public void remove(int index) {
        if (index < 0) {
            return;
        }
        IBaseRequest request = reqCache.remove(index);
        request = null;
    }

    public int size() {
        return reqCache.size();
    }

    /**
     * 移除最末尾
     */
    public void removeLast() {
        remove(reqCache.size() - 1);
    }

    public void clear() {
        reqCache.clear();
    }
}
