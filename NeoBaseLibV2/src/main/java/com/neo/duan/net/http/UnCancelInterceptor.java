package com.neo.duan.net.http;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : neo.duan
 * @date : 	 2016/8/30 0030
 * @desc : 不用取消请求过滤器
 */
public class UnCancelInterceptor {
    public static final List<Class> requests;

    //定义哪些请求不用取消
    static {
        requests = new ArrayList<>();
        //加入购物车
//        requests.add(AddCartReq.class);
    }
}
