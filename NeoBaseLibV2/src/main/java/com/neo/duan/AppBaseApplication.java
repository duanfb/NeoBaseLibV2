package com.neo.duan;

import android.support.multidex.MultiDexApplication;

import com.neo.duan.entity.AppInfo;
import com.neo.duan.entity.DeviceInfo;
import com.neo.duan.manager.ImageManager;
import com.neo.duan.utils.LogUtils;
import com.neo.duan.utils.PermissionsChecker;
import com.orhanobut.logger.Logger;


/**
 * Author: neo.duan
 * Date: 2017/02/18
 * Desc: app
 */
public abstract class AppBaseApplication extends MultiDexApplication {
    protected final String TAG = AppBaseApplication.class.getSimpleName();
    private static AppBaseApplication instance;

    public static AppBaseApplication getInstance() {
        return instance;
    }

    private AppInfo mAppInfo; //app信息
    private DeviceInfo mDeviceInfo; //device信息

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化打印日志
        initLogger();

        //初始化应用图片加载库
        initImageLoader();

        //初始化异常管理器
//        Thread.setDefaultUncaughtExceptionHandler(ExceptionManager.getInstance());

        //初始化设备信息：判断是否有权限
        if (getPermissions() != null && getPermissions().length > 0) {
            PermissionsChecker mPermissionsChecker = new PermissionsChecker(this);
            boolean hasAllPermissions = !mPermissionsChecker.lacksPermissions(getPermissions());
            // 缺少权限时,不初始化设备，一般在Splash页面做
            if (hasAllPermissions) {
                LogUtils.d(TAG, "hasAllPermissions initDeviceAndApp");
                initDeviceAndApp();
            }
        }
    }

    private void initLogger() {
        Logger.init();
    }

    private void initImageLoader() {
        ImageManager.getInstance().init(this);
    }

    /**
     * 初始化设备信息和app信息，6.0需要权限，在BaseActivity中处理
     */
    public void initDeviceAndApp() {
        if (mAppInfo == null) {
            mAppInfo = AppInfo.init(this);
        }
        if (mDeviceInfo == null) {
            mDeviceInfo = DeviceInfo.init(this);
        }
    }

    /**
     * 获取App信息
     * @return AppInfo
     */
    public AppInfo getAppInfo() {
        if (mAppInfo == null) {
            mAppInfo = AppInfo.init(this);
        }
        return mAppInfo;
    }

    /**
     * 获取设备信息
     * @return DeviceInfo
     */
    public DeviceInfo getDeviceInfo() {
        if (mDeviceInfo == null) {
            mDeviceInfo = DeviceInfo.init(this);
        }
        return mDeviceInfo;
    }

    /**
     * 获取权限数组信息
     * @return Permissions
     */
    public abstract String[] getPermissions();
}
