package com.neo.duan.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;

import com.neo.duan.R;

import java.util.List;

/**
 * Author: neo.duan
 * Date: 2017/02/18
 * Desc: 有关app信息工具类
 */
public class AppUtils {

    private AppUtils() {
        throw new AssertionError();
    }

    /**
     * whether this process is named with processName
     * 
     * @param context
     * @param processName
     * @return <ul>
     *         return whether this process is named with processName
     *         <li>if context is null, return false</li>
     *         <li>if {@link ActivityManager#getRunningAppProcesses()} is null, return false</li>
     *         <li>if one process of {@link ActivityManager#getRunningAppProcesses()} is equal to processName, return
     *         true, otherwise return false</li>
     *         </ul>
     */
    public static boolean isNamedProcess(Context context, String processName) {
        if (context == null) {
            return false;
        }

        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
        if (ListUtils.isEmpty(processInfoList)) {
            return false;
        }

        for (RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo != null && processInfo.pid == pid
                    && ObjectUtils.isEquals(processName, processInfo.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * whether application is in background
     * <ul>
     * <li>need use permission android.permission.GET_TASKS in Manifest.xml</li>
     * </ul>
     * 
     * @param context
     * @return if application is in background return true, otherwise return false
     */
    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取app版本号
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
            PackageInfo pkgInfo = null;
            try {
                pkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (pkgInfo != null) {
                return pkgInfo.versionName;
            }
            return "";
    }

    /**
     * 获取app version code
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pkgInfo != null) {
            return pkgInfo.versionCode;
        }
        return -1;
    }

    /**
     * 获取app名称
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        return context.getResources().getString(R.string.app_name);
    }
}
