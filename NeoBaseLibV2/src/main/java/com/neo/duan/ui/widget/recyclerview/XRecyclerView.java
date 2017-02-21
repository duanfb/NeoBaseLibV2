package com.neo.duan.ui.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


/**
 * Author: neo.duan
 * Date: 2017/02/21
 * Desc: 封装系统的RecyclerView，方便日后需求变动的维护
 */
public class XRecyclerView extends RecyclerView {

    public XRecyclerView(Context context) {
        this(context, null);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化设置默认的一些属性
     */
    private void init() {
        // 设置水平布局
        this.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        // 设置item动画
        this.setItemAnimator(new DefaultItemAnimator());
    }
}
