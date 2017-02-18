package com.neo.duan.utils;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author: neo.duan
 * Date: 2017/02/18
 * Desc: 有关设备工具类
 */
public class DeviceUtils {

    /**
     * 获取设备MAC Permission: android.permission.ACCESS_WIFI_STATE
     *
     * @param context
     * @return
     */
    public static String getMAC(Context context) {
        WifiManager wManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wManager.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获取设备ID
     *
     * @param context
     * @return
     */
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取当前的imei, 可能为空 Permission: android.permission.READ_PHONE_STATE
     */
    public static String getImei(Context context) {
        TelephonyManager tManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tManager.getDeviceId();
    }

    /**
     * 获取当前的imsi, 可能为空 Permission: android.permission.READ_PHONE_STATE
     */
    public static String getImsi(Context context) {
        TelephonyManager tManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tManager.getSubscriberId();
    }

    /**
     * 获取设备厂商
     *
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 是否是电信卡 Permission: android.permission.READ_PHONE_STATE
     *
     * @param context
     * @return
     */
    public static boolean isCTC(Context context) {
        String imsi = getImei(context);
        return isCTC(imsi);
    }

    /**
     * 是否是电信卡 Permission: android.permission.READ_PHONE_STATE
     *
     * @param imsi
     * @return
     */
    public static boolean isCTC(String imsi) {
        return !TextUtils.isEmpty(imsi) && imsi.startsWith("46003");
    }

    /**
     * 是否是移动卡 Permission: android.permission.READ_PHONE_STATE
     *
     * @param context
     * @return
     */
    public static boolean isCMCC(Context context) {
        String imsi = getImei(context);
        return isCMCC(imsi);
    }

    /**
     * @param imsi
     * @return
     */
    public static boolean isCMCC(String imsi) {
        return !TextUtils.isEmpty(imsi)
                && (imsi.startsWith("46000")
                || imsi.startsWith("46002"));
    }

    /**
     * 是否是联通卡 Permission: android.permission.READ_PHONE_STATE
     *
     * @param context
     * @return
     */
    public static boolean isCUC(Context context) {
        String imsi = getImei(context);
        return isCUC(imsi);
    }

    /**
     * @param imsi
     * @return
     */
    public static boolean isCUC(String imsi) {
        return !TextUtils.isEmpty(imsi) && imsi.startsWith("46001");
    }

    /**
     * 判断是否是平板
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取状态栏的高度，系统默认高度为25dp
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        java.lang.reflect.Field field;
        int x;
        int statusBarHeight;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ScreenUtils.dpToPxInt(context,25);
    }

    public static int getAndroidSDKVersionInt() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断当前设备是否是模拟器。如果返回TRUE，则当前是模拟器，不是返回FALSE
     *
     * @param context
     * @return
     */
    public static boolean isEmulator(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if (imei != null && imei.equals("000000000000000")) {
                return true;
            }
            return (Build.MODEL.equals("sdk")) || (Build.MODEL.equals("google_sdk"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取手机的唯一识别号
     *
     * @return
     */
    public static String getUniqueID(Context context) {
        String m_szDevIDShort = null;
        String m_szUniqueID = new String();
        try {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String szImei = TelephonyMgr.getDeviceId();

            m_szDevIDShort = "56" + // we make this look like a valid IMEI
                    Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
                    + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
                    + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
                    + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
                    + Build.USER.length() % 10; // 13 digits

            String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();

            BluetoothAdapter m_BluetoothAdapter; // Local Bluetooth adapter
            m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            String m_szBTMAC = m_BluetoothAdapter.getAddress();

            String m_szLongID = szImei + m_szDevIDShort + m_szAndroidID + m_szWLANMAC + m_szBTMAC;
            // compute md5
            MessageDigest m = null;
            try {
                m = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
            // get md5 bytes
            byte p_md5Data[] = m.digest();
            // create a hex string

            for (int i = 0; i < p_md5Data.length; i++) {
                int b = (0xFF & p_md5Data[i]);
                // if it is a single digit, make sure it have 0 in front (proper padding)
                if (b <= 0xF)
                    m_szUniqueID += "0";
                // add number to string
                m_szUniqueID += Integer.toHexString(b);
            } // hex string to uppercase
            m_szUniqueID = m_szUniqueID.toUpperCase();
        } catch (Exception e) {
            m_szUniqueID = m_szDevIDShort;
            e.printStackTrace();
        }
        return m_szUniqueID;
    }

    /**
     * 获取屏幕宽
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            return display.getWidth();
        } else {
            return getScreenPoint(context).x;
        }
    }

    /**
     * 获取屏幕高
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            return display.getHeight();
        } else {
            return getScreenPoint(context).y;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenPoint(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        Display display = wm.getDefaultDisplay();
        display.getSize(point);
        return point;
    }
}
