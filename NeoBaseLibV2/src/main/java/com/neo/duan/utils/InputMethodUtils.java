package com.neo.duan.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Author: neo.duan
 * Date: 2017/02/18
 * Desc: 关于键盘显示工具类 thanks:http://blog.csdn.net/mkfrank/article/details/50522666
 */
public class InputMethodUtils {
    /**
     * 显示软键盘
     */
    public static void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * 显示软键盘
     */
    public static void showInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 多少时间后显示软键盘
     */
    public static void showInputMethod(final View view, long delayMillis) {
        // 显示输入法
        view.postDelayed(new Runnable() {

            @Override
            public void run() {
                InputMethodUtils.showInputMethod(view);
            }
        }, delayMillis);
    }

    /**
     * 隐藏软键盘
     * @param view
     */
    public static void hideInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            IBinder iBinder = view.getWindowToken();
            if (iBinder != null) {
                imm.hideSoftInputFromWindow(iBinder, 0);
            }
        }
    }
}
