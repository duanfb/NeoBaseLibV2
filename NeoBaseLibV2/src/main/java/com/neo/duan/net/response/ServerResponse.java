package com.neo.duan.net.response;

/**
 * @author : neo.duan
 * @date : 	 2016/8/17 0017
 * @desc : 服务器返回的数据结构
 */
public class ServerResponse {
    private static final int SUCCESS = 1;

    private int status;
    private String params;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public boolean isSuccess() {
        return SUCCESS == status;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status='" + status + '\'' +
                ", params=" + params +
                '}';
    }
}
