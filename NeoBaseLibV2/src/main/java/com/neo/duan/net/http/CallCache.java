package com.neo.duan.net.http;

import com.neo.duan.net.request.base.BaseRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;

/**
 * @author : neo.duan
 * @date : 	 2016/10/11
 * @desc : 管理有关请求的Call的缓存处理
 */
public class CallCache {
    private List<CallInfo> callCache = new ArrayList<>();

    public CallCache() {
        Collections.synchronizedList(callCache);
    }

    public void add(BaseRequest request, Call call) {
        CallInfo info = new CallInfo();
        info.key = request.getApi();
        info.call = call;
        callCache.add(0, info);
    }

    /**
     * 获取该请求对应的Call
     *
     * @param request
     * @return
     */
    public Call get(BaseRequest request) {
        if (request == null) {
            return null;
        }
        for (CallInfo info : callCache) {
            if (request.getApi().equals(info.key)) {
                return info.call;
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
    public Call get(int index) {
        if (index < 0) {
            return null;
        }
        CallInfo callInfo = callCache.get(index);
        if (callInfo == null) {
            return null;
        }
        return callInfo.call;
    }

    /**
     * 获取末尾对应的Call
     *
     * @return
     */
    public Call getLast() {
        return get(callCache.size() - 1);
    }

    /**
     * 集合中是否缓存该请求
     *
     * @param request
     * @return
     */
    public boolean contails(BaseRequest request) {
        if (request == null) {
            return false;
        }
        for (CallInfo info : callCache) {
            if (request.getApi().equals(info.key)) {
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
    public void remove(BaseRequest request) {
        if (request == null) {
            return;
        }
        Iterator<CallInfo> iterator = callCache.iterator();
        while (iterator.hasNext()) {
            CallInfo info = iterator.next();
            if (request.getApi().equals(info.key)) {
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
        callCache.remove(index);
    }

    public int size() {
        return callCache.size();
    }

    /**
     * 移除最末尾
     */
    public void removeLast() {
        remove(callCache.size() - 1);
    }

    public void clear() {
        callCache.clear();
    }

    /**
     * 代表一个请求数据结构
     */
    class CallInfo {
        public String key;
        public Call call;
    }
}
