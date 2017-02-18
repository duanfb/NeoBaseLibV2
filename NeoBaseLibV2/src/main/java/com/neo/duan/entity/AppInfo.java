package com.neo.duan.entity;

import android.content.Context;

import com.neo.duan.utils.AppUtils;


/**
 * Author: neo.duan
 * Date: 2017/02/18
 * Desc: 有关app信息
 */
public class AppInfo {
    public String appName; //app名称
    public String versionName; //版本号
    public int versionCode; //代码版本号
    public boolean isInBackground; //是否在后台运行

    public static AppInfo init(Context context) {
        AppInfo info = new AppInfo();
        info.appName = AppUtils.getAppName(context);
        info.versionName = AppUtils.getVersionName(context);
        info.versionCode = AppUtils.getVersionCode(context);
        info.isInBackground = AppUtils.isApplicationInBackground(context);
        return info;
    }

}
