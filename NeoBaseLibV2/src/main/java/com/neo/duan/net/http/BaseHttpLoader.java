package com.neo.duan.net.http;


import com.neo.duan.AppBaseApplication;
import com.neo.duan.net.HttpLoaderConfiguration;
import com.neo.duan.net.response.ServerResponse;
import com.neo.duan.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * Author: neo.duan
 * Date: 2017/02/20
 * Desc: 公共请求基类  see:http://wuxiaolong.me/2016/06/18/retrofits/
 */
public class BaseHttpLoader {
    protected static final String TAG = BaseHttpLoader.class.getSimpleName();
    protected ApiService mApiService;

    protected BaseHttpLoader() {

    }

    protected static Retrofit mRetrofit;

    protected void initBase(HttpLoaderConfiguration configuration) {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(configuration.serverHost) //配置服务器地址
                    .addConverterFactory(FastJsonConverterFactory.create()) //配置FastJson解析器
                    .client(okHttpClient)
                    .build();

            //是否开启数据缓存
            if (configuration.enableCache) {
                builder.cache(getCache(configuration))//设置缓存目录
                        .addInterceptor(getCacheInterceptor(configuration));//添加缓存机制
            }

            //设置头
            builder.addInterceptor(createHeaderInterceptor());

            //设置超时
            builder.connectTimeout(configuration.timeout, TimeUnit.SECONDS);
            builder.readTimeout(configuration.timeout, TimeUnit.SECONDS);
            builder.writeTimeout(configuration.timeout, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);
        }
        mApiService = mRetrofit.create(ApiService.class);
    }

    /**
     * 设置缓存信息
     *
     * @return Cache
     */
    private Cache getCache(HttpLoaderConfiguration configuration) {
        File cacheFile = new File(AppBaseApplication.getInstance().getExternalCacheDir(), configuration.cacheDir);
        //设置最大缓存size
        return new Cache(cacheFile, 1024 * 1024 * 50);
    }

    /**
     * 获取缓存拦截器，定义一些规则
     */
    private Interceptor getCacheInterceptor(final HttpLoaderConfiguration configuration) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //网络不可用的时候，强制使用缓存
                if (!NetWorkUtils.isAvailable(AppBaseApplication.getInstance())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetWorkUtils.isAvailable(AppBaseApplication.getInstance())) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    long maxStale = configuration.cacheTime;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("nyn")
                            .build();
                }
                return response;
            }
        };
    }

    /**
     * 添加头部拦截器
     * @return Interceptor
     */
    private Interceptor createHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Connection", "close")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    /**
     * @author : neo.duan
     * @date : 	 2016/7/25 0025
     * @desc : 与服务器有关的API接口
     */
    protected interface ApiService {

        /**
         * 通用post请求
         *
         * @param paramMap 参数Map
         * @param url url
         * @return Call
         */
        @FormUrlEncoded
        @POST
        Call<ServerResponse> sendPost(@FieldMap Map<String, Object> paramMap, @Url String url);

        /**
         * 通用get请求，自己拼接带参数的请求地址
         *
         * @param url url
         * @return Call
         */
        @GET
        Call<ServerResponse> sendGet(@Url String url);

        /**
         * 通用上传请求
         *
         * @return Call
         */
        @Multipart
        @POST
        Call<ServerResponse> upload(@PartMap Map<String, RequestBody> params,
                                  @Part MultipartBody.Part file, @Url String url);
    }
}
