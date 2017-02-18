package com.neo.duan.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * ScreenUtils
 * <ul>
 * <strong>Convert between dp and sp</strong>
 * <li>{@link ScreenUtils#dpToPx(Context, float)}</li>
 * <li>{@link ScreenUtils#pxToDp(Context, float)}</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-14
 */
public class ScreenUtils {

    private ScreenUtils() {
        throw new AssertionError();
    }

    public static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());
    }

    public static float pxToDp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                px, context.getResources().getDisplayMetrics());
    }

    public static int dpToPxInt(Context context, float dp) {
        return (int)(dpToPx(context, dp) + 0.5f);
    }

    public static int pxToDpCeilInt(Context context, float px) {
        return (int)(pxToDp(context, px) + 0.5f);
    }

    /**
     * 获取屏幕宽
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
