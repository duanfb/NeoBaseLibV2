package com.neo.duan.utils;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;

/**
 * 日志工具类
 */
public class LogUtils {

    private static final int LOG_LEVEL = Log.INFO;

    /**
     * 根据日志的级别/TAG 来判断是否要输出该级别的数据</br/>
     * 可以使用
     * <i>adb shell setprop log.tag.XX VERBOSE</i>
     * 来打开所有的日志 <br/>
     * 也可以使用<i>adb shell setprop log.tag.tag VERBOSE</i>打开部分的权限
     *
     * @param level 日志的级别
     * @param tag   日志的tag
     * @return 是否输出该级别的日志
     */
    private static boolean shouldLog(int level, String tag) {
        return true;
//        return BuildConfig.LOG_DEBUG || level >= LOG_LEVEL || Log.isLoggable(tag, level) || Log.isLoggable("XX", Log.VERBOSE);
    }


    public static void i(String tag, String message) {
        tag = ellipsiseTag(tag);
        if (shouldLog(Log.INFO, tag))
            Logger.t(tag).i(message);
    }

    public static void i(String tag, String message, Throwable throwable) {
        tag = ellipsiseTag(tag);
        if (shouldLog(Log.INFO, tag))
            Logger.i(tag, message, throwable);

    }

    public static void i(Object tag, String message) {
        i(tag.getClass().getSimpleName(), message);
    }

    public static void i(Object tag, String message, Throwable throwable) {
        i(tag.getClass().getSimpleName(), message, throwable);
    }

    public static void d(String tag, String message) {
        tag = ellipsiseTag(tag);
        if (shouldLog(Log.DEBUG, tag))
            Logger.t(tag).d(message);
    }

    public static void d(String tag, String message, Throwable throwable) {
        tag = ellipsiseTag(tag);
        if (shouldLog(Log.DEBUG, tag))
            Logger.t(tag).d( message, throwable);
    }

    public static void d(String tag, Object obj) {
        tag = ellipsiseTag(tag);
        if (shouldLog(Log.DEBUG, tag))
            Logger.t(tag).d(obj);
    }

    public static void d(String tag, JSONObject obj) {
        tag = ellipsiseTag(tag);
        if (shouldLog(Log.DEBUG, tag))
            Logger.t(tag).json(obj.toString());
    }

    public static void json(String tag, String json) {
        tag = ellipsiseTag(tag);
        if (shouldLog(Log.DEBUG, tag))
            Logger.t(tag).json(json);
    }

    public static void d(Object tag, String message) {
        d(tag.getClass().getSimpleName(), message);
    }


    public static void w(String tag, String message) {
        tag = ellipsiseTag(tag);
        if (shouldLog(Log.WARN, tag))
            Logger.t(tag).w(message);
    }

    public static void w(String tag, String message, Throwable throwable) {
        tag = ellipsiseTag(tag);
        if (shouldLog(Log.WARN, tag))
            Logger.t(tag).w(message, throwable);
    }

    public static void e(String tag, String message) {
        tag = ellipsiseTag(tag);
        if (shouldLog(Log.ERROR, tag))
            Logger.t(tag).e(message);
    }

    public static void e(String tag, String message, Throwable throwable) {
        tag = ellipsiseTag(tag);
        if (shouldLog(Log.ERROR, tag))
            Logger.t(tag).e(message, throwable);
    }

    /**
     * 根据Android规范,TAG最大不应该超过23
     *
     * @param tag 要check的TAG
     * @return 处理过的tag
     */
    private static String ellipsiseTag(String tag) {
        if (StringUtils.isBlank(tag)) {
            return "";
        }
        if (tag.length() <= 23) {
            return tag;
        }
        return tag.substring(0, 23);
    }
}
