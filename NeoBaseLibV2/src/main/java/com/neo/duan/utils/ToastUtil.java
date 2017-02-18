package com.neo.duan.utils;

import android.app.Activity;
import android.widget.Toast;

import com.neo.duan.manager.ScreenManager;


/**
 * Author: neo.duan
 * Date: 2017/02/18
 * Desc: 吐司工具类
 */
public class ToastUtil {

    public static Toast toast;

    private ToastUtil() {
        throw new AssertionError();
    }
    
    public static void show(CharSequence text){
        show(getContext(), text, Toast.LENGTH_SHORT);
    }

    public static void show(int resId) {
        Activity context = getContext();
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        Activity context = getContext();
        show(context, context.getResources().getText(resId), duration);
    }

    private static void show(Activity context, CharSequence text, int duration) {
        if (context == null || context.isFinishing()) {
            return;
        }
        if (StringUtils.isBlank(text + "")) {
            return;
        }

        if (toast == null) {
            toast = Toast.makeText(context, text,duration);
        } else {
            toast.setDuration(duration);
            toast.setText(text);
        }
        toast.show();


//        Toast.makeText(context,text,duration);

        //see:https://github.com/johnkil/Android-AppMsg
//        可设置动画，自定义View，弹出优先级等等

//        AppMsg appMsg = AppMsg.makeText(context, text, AppMsg.STYLE_ALERT);
//        appMsg.setLayoutGravity(Gravity.CENTER);
//        appMsg.setParent(R.id.fl_msg_content);
//        appMsg.setAnimation(R.anim.slide_in_top, R.anim.slide_out_top);
//        switch (duration) {
//            case Toast.LENGTH_SHORT:
//                appMsg.setDuration(AppMsg.LENGTH_SHORT);
//                break;
//            case Toast.LENGTH_LONG:
//                appMsg.setDuration(AppMsg.LENGTH_LONG);
//                break;
//        }
//        appMsg.show();
    }

    public static void show(int resId, Object... args) {
        Activity context = getContext();
        show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(String format, Object... args) {
        Activity context = getContext();
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration, Object... args) {
        Activity context = getContext();
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(String format, int duration, Object... args) {
        Activity context = getContext();
        show(context, String.format(format, args), duration);
    }

    public static void canAll() {
        Activity context = getContext();
        if (context == null || context.isFinishing()) {
            return;
        }
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 使用当前Activity
     * @return
     */
    public static Activity getContext() {
        return ScreenManager.getInstance().currentActivity();
    }
}