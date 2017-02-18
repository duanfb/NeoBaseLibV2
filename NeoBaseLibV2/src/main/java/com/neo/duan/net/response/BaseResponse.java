package com.neo.duan.net.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author : neo.duan
 * @date : 	 2016/9/20
 * @desc : 服务器返回实体基类
 */
public class BaseResponse<T> {
    private static final String SUCCESS = "S";

    @JSONField(name = "Nonce")
    private String nonce;

    @JSONField(name = "Result")
    private String result;

    @JSONField(name = "ErrorCode")
    private String errorCode;

    @JSONField(name = "ErrorMessage")
    private String errorMessage;

    @JSONField(name = "message")
    private String message;

    @JSONField(name = "ResponseData")
    private T data;

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return SUCCESS.equals(result);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


