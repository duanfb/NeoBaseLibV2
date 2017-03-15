package com.neo.duan.net.request;

import java.util.Map;

/**
 * Author: neo.duan
 * Date: 2017/02/20 10:03
 * Desc: Base请求接口
 */
public interface IBaseRequest {

    /**
     * 获取Map参数信息
     *
     * @return String
     */
    Map<String, Object> getParams();

    /**
     * 获取API信息
     *
     * @return String
     */
    String getApi();

    /**
     * 获取返回数据Class
     *
     * @return Class
     */
    Class getResponseClazz();

}
