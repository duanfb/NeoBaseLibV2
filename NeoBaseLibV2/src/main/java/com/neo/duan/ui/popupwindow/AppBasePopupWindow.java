package com.neo.duan.ui.popupwindow;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.neo.duan.R;
import com.neo.duan.mvp.view.base.IBaseView;

/**
 * Author: neo.duan
 * Date: 2017/02/21
 * Desc: 通用BasePopupWindow
 */
public abstract class AppBasePopupWindow extends PopupWindow implements IBaseView {
    private static final String TAG = AppBasePopupWindow.class.getSimpleName();

    protected Context mContext;

    protected View mContentView;

    public AppBasePopupWindow() {
        super();
    }

    public AppBasePopupWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AppBasePopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppBasePopupWindow(Context context) {
        super(context);
    }

    public AppBasePopupWindow(int width, int height) {
        super(width, height);
    }

    public AppBasePopupWindow(View contentView, int width, int height,
                              boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public AppBasePopupWindow(View contentView) {
        super(contentView);
    }

    public AppBasePopupWindow(Context context, View contentView, int width, int height) {
        super(contentView, width, height, true);
        this.mContext = context;
        mContentView = contentView;
        init();
    }

    public void init() {
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.AnimBottom);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setBackgroundDrawable(null);
        setTouchable(true);
        initViews();
        initEvents();
        initData();
        //设置点击内容其他区域消失
        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return false;
            }
        });
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    public abstract void initLayouts();

    public abstract void initViews();

    public abstract void initListeners();

    public abstract void initEvents();

    public abstract void initData();

    public View findViewById(int id) {
        return mContentView.findViewById(id);
    }

    /**
     * 设置显示在mDropView的下方
     * @param view
     */
    public void setShowAsDropDownView(View view) {
        if (!isShowing() && view != null) {
            showAsDropDown(view);
        }
        applyDim();
    }

    /**
     * 从view上方弹出
     */
    public void setShowAsDropUpView(View view) {
        if (!isShowing() && view != null) {
            showAtLocation(view,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }

        applyDim();
    }

    /**
     * 设置背景dim效果
     */
    private void applyDim() {
        View contentView;
        //android 6.0 用该方法
        if (Build.VERSION.SDK_INT >= 23) {
            contentView = (View) getContentView().getParent();
        } else {
            contentView = mContentView;
        }

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) contentView.getLayoutParams();
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.3f;
        wm.updateViewLayout(contentView, params);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @Override
    public void showNetError() {

    }

    @Override
    public void showEmptyView() {

    }

}
