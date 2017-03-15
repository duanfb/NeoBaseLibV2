package com.neo.duan.utils;

import android.text.TextUtils;

/**
 * Author: neo.duan
 * Date: 2017/02/18
 * Desc: 数据类型转换工具类
 */
public class ConvertUtils {
    private ConvertUtils() {
        throw new AssertionError();
    }

    /**
     * String 转 long
     * @param value
     * @param defaultValue
     * @return
     */
    public static long convert2Long(String value, long defaultValue) {
        if (TextUtils.isEmpty(value)) {
            return defaultValue;
        }
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * String 转 int
     * @param value
     * @param defaultValue
     * @return
     */
    public static int convert2Int(String value, int defaultValue) {
        if (TextUtils.isEmpty(value)) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * String 转 float
     * @param value
     * @param defaultValue
     * @return
     */
    public static float convert2Float(String value, float defaultValue) {
        if (TextUtils.isEmpty(value)) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
