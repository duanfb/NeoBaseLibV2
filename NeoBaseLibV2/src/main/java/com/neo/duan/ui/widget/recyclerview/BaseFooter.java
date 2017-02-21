package com.neo.duan.ui.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * @author : neo.duan
 * @date : 	 2016/6/30 0030
 * @desc : recycleView的Footer基类
 */
public abstract class BaseFooter extends FrameLayout {
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;

    protected Context mContext;

    public BaseFooter(Context context) {
        this(context, null);
    }

    public BaseFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(layoutParams);

        View footerView = getView(LayoutInflater.from(mContext), this);
        onViewCreated(footerView);
    }


    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                loading();
                break;
            case STATE_COMPLETE:
                loadComplete();
                break;
            case STATE_NOMORE:
                noMore();
                break;
        }
    }


    public abstract View getView(LayoutInflater inflater, ViewGroup viewGroup);
    public abstract View onViewCreated(View footerView);
    public abstract void loading();
    public abstract void loadComplete();
    public abstract void noMore();
}
