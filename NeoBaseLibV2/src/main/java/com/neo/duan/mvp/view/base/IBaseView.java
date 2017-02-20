package com.neo.duan.mvp.view.base;

import android.content.Context;

/**
 * Author: neo.duan
 * Date: 2017/02/20 10:41
 * Desc: mvp中View基类
 */
public interface IBaseView {

    /**
     * 获取上下文
     * @return
     */
    Context getContext();

    /**
     * 初始化内容布局
     */
    void initLayouts();

    /**
     * 初始化内容view
     */
    void initViews();

    /**
     * 初始化事件监听
     */
    void initListeners();

    /**
     * 初始化数据
     */
    void initData();


    /**
     * 加载框
     */
    void showLoading();

    /**
     * 显示带文本加载框
     * @param msg
     */
    void showLoading(String msg);

    /**
     * 隐藏加载框
     */
    void hideLoading();

    /**
     * 弹出错误提示
     *
     * @param errorMsg
     */
    void showErrorMsg(String errorMsg);

    /**
     * 网络错误View
     */
    void showNetError();

    /**
     * 显示空数据View
     */
    void showEmptyView();
}
