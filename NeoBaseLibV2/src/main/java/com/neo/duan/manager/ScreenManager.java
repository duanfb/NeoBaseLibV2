package com.neo.duan.manager;

import android.app.Activity;

import java.util.Stack;

/**
 * Author: neo.duan
 * Date: 2017/02/18
 * Desc: 以堆栈方式管理历史Activity
 */
public class ScreenManager {
    private static Stack<Activity> activityStack;
    private static ScreenManager instance;

    private ScreenManager() {
    }

    /**
     * 视图管理器，用于完全退出
     *
     * @return
     */
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    /**
     * 回收堆栈中指定的activity
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 获取堆栈的栈顶activity
     *
     * @return 栈顶activity
     */
    public Activity currentActivity() {
        Activity activity = null;
        try {
            if (!activityStack.isEmpty()) {
                activity = activityStack.peek(); //获取栈顶对象而不移除它
            }
            return activity;
        } catch (Exception ex) {
            System.out.println("ScreenManager:currentActivity---->"
                    + ex.getMessage());
            return activity;
        } finally {
            activity = null;
        }
    }

    /**
     * 将activity压入堆栈
     *
     * @param activity 需要压入堆栈的activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.push(activity);
    }

    /**
     * 回收顶部Activity
     */
    public void popTopActivity() {
        popActivity(currentActivity());
    }

    /**
     * 回收堆栈中所有Activity
     */
    public void popAllActivity() {
        Activity activity = null;
        try {
            while (!activityStack.isEmpty()) {
                activity = currentActivity();
                if (activity != null) {
                    popActivity(activity);
                }
            }
        } finally {
            activity = null;
        }
    }
}