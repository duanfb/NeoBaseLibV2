package com.neo.duan.net.response;

/**
 * Author: neo.duan
 * Date: 2017/05/10 13:50
 * Desc: 服务器返回数据结构
 */
public interface IServerResponse {

    /**
     * 获取服务器返回的状态码
     * @return String
     */
    String getStatus();

    /**
     * 是否成功
     *
     * @return boolean
     */
    boolean isSuccess();

    /**
     * 返回需要处理的实体类数据
     *
     * @return String
     */
    String getParams();
}
