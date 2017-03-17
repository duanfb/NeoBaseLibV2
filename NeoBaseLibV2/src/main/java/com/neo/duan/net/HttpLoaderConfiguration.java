package com.neo.duan.net;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: neo.duan
 * Date: 2017/02/20 17:06
 * Desc: HttpLoader配置器
 */
public final class HttpLoaderConfiguration {
    public final String serverHost; //服务器Host
    public final boolean enableCache; //是否支持缓存
    public final String cacheDir; //数据缓存目录
    public final long cacheTime; //缓存时长
    public final long timeout; //超时时间
    public final List<Class> unCancelReqList; //不需要拦截的请求

    public HttpLoaderConfiguration(Builder builder) {
        this.serverHost = builder.serverHost;
        this.enableCache = builder.enableCache;
        this.cacheDir = builder.cacheDir;
        this.cacheTime = builder.cacheTime;
        this.timeout = builder.timeout;
        this.unCancelReqList = builder.unCancelReqList;
    }

    public static HttpLoaderConfiguration createDefault() {
        return (new HttpLoaderConfiguration.Builder()).build();
    }

    /**
     * 自定义HttpLoader的Builder模块
     */
    public static final class Builder {

        String serverHost; //服务器Host
        boolean enableCache; //是否支持缓存
        String cacheDir; //数据缓存目录
        long cacheTime; //缓存时长
        long timeout; //超时时间秒
        List<Class> unCancelReqList; //不需要拦截的请求

        public Builder() {
            this.serverHost = "http://www.baidu.com/abc/";
            this.enableCache = true;
            this.cacheDir = "appCache";
            this.cacheTime = 60 * 60 * 24 * 28; //默认4周
            this.timeout = 15; //默认15秒
            this.unCancelReqList = new ArrayList<>();
        }

        public Builder setServerHost(String serverHost) {
            this.serverHost = serverHost;
            return this;
        }

        public Builder setEnableCache(boolean enableCache) {
            this.enableCache = enableCache;
            return this;
        }

        public Builder setCacheDir(String cacheDir) {
            this.cacheDir = cacheDir;
            return this;
        }

        public Builder setCacheTime(long cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public Builder setTimeout(long timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder setUnCancelReqList(List<Class> unCancelReqList) {
            this.unCancelReqList = unCancelReqList;
            return this;
        }

        public HttpLoaderConfiguration build() {
            return new HttpLoaderConfiguration(this);
        }
    }
}
