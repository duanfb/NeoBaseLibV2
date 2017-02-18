package com.neo.duan.net.request.base;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : neo.duan
 * @date : 	 2016/7/26 0026
 * @desc : 各个请求基类
 */
public abstract class BaseRequest {
    private static final String TAG = BaseRequest.class.getSimpleName();

    public Map<String, Object> params = new HashMap<>();

    public abstract String getApi();

    public void add(String param, Object paramValue) {
        params.put(param.trim(), paramValue);
    }

    public Map<String, Object> getParams() {
        return handleCommonParams();
    }


    /**
     * 添加公共参数
     */
    private Map<String, Object> handleCommonParams() {
        return new HashMap<>();
    }

//    public abstract Class<? extends BaseInfo> getResponseClazz();
}
