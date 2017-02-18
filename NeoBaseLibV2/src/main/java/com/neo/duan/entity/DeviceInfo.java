package com.neo.duan.entity;

import android.content.Context;

import com.neo.duan.utils.DeviceUtils;
import com.neo.duan.utils.LogUtils;


/**
 * Author: neo.duan
 * Date: 2017/02/18
 * Desc: 设备信息
 */
public class DeviceInfo {
    public String deviceId; //设备id
    public int screenWith; //屏幕宽度
    public int screenHeight; //屏幕高度
    public int statusBarHeight; //状态栏高度
    public int sdkVersion; //sdk版本
    public String wifiMac; //wifi mac地址
    public String androidId; //androd Id
    public String imei; //
    public String imsi;
    public String manufacturer; //厂商
    public boolean isCTC; //是否电信卡
    public boolean isCMCC; //是否移动卡
    public boolean isCUC; //是否联通卡
    public boolean isTablet; //是否平板
    public boolean isEmulator; //是否模拟器

    public static DeviceInfo init(Context context) {
        DeviceInfo info = new DeviceInfo();
        info.deviceId = DeviceUtils.getUniqueID(context);
        info.screenWith = DeviceUtils.getScreenWidth(context);
        info.screenHeight = DeviceUtils.getScreenHeight(context);
        info.statusBarHeight = DeviceUtils.getStatusBarHeight(context);
        info.sdkVersion = DeviceUtils.getAndroidSDKVersionInt();
        info.wifiMac = DeviceUtils.getMAC(context);
        info.androidId = DeviceUtils.getAndroidID(context);
        info.imei = DeviceUtils.getImei(context);
        info.imsi = DeviceUtils.getImsi(context);
        info.manufacturer = DeviceUtils.getManufacturer();
        info.isCTC = DeviceUtils.isCTC(context);
        info.isCMCC = DeviceUtils.isCMCC(context);
        info.isCUC = DeviceUtils.isCUC(context);
        info.isTablet = DeviceUtils.isTablet(context);
        info.isEmulator = DeviceUtils.isEmulator(context);

        LogUtils.d("DeviceInfo", info.toString());
        return info;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "deviceId='" + deviceId + '\'' +
                ", screenWith=" + screenWith +
                ", screenHeight=" + screenHeight +
                ", statusBarHeight=" + statusBarHeight +
                ", sdkVersion=" + sdkVersion +
                ", wifiMac='" + wifiMac + '\'' +
                ", androidId='" + androidId + '\'' +
                ", imei='" + imei + '\'' +
                ", imsi='" + imsi + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", isCTC=" + isCTC +
                ", isCMCC=" + isCMCC +
                ", isCUC=" + isCUC +
                ", isTablet=" + isTablet +
                ", isEmulator=" + isEmulator +
                '}';
    }
}
