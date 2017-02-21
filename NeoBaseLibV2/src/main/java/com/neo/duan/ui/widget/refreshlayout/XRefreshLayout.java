package com.neo.duan.ui.widget.refreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.neo.duan.utils.LogUtils;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author : neo.duan
 * @date : 	 2016/11/10 0010
 * @desc : 请描述该文件
 */
public class XRefreshLayout extends PtrClassicFrameLayout {

    private int lastX;
    private int lastY;
    private OnRefreshListener mRefreshListener;

    public XRefreshLayout(Context context) {
        this(context, null);
    }

    public XRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mRefreshListener = listener;
    }

    private void init() {
        this.setLastUpdateTimeRelateObject(this);
        this.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //开始下拉刷新
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        this.setLastUpdateTimeRelateObject(this);
        // the following are default settings
        this.setResistance(1.7f);
        this.setRatioOfHeaderHeightToRefresh(1.2f);
        this.setDurationToClose(200);
        this.setDurationToCloseHeader(700); //设置关闭下拉刷新动画时长
        // default is false
        this.setPullToRefresh(false);
        // default is true
        this.setKeepHeaderWhenRefresh(true);
    }

    /**
     * 手动调用下拉刷新
     */
    public void callRefresh() {
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                autoRefresh();
            }
        }, 100);
    }

    /**
     * 回调接口
     */
    public interface OnRefreshListener {
        /**
         * 下拉刷新，回调接口
         */
        void onRefresh();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - lastY;
                int deltaX = x - lastX;
                if (Math.abs(deltaX) < Math.abs(deltaY)) {
                    LogUtils.d("XRefreshLayout", "向下拉");
                    return super.dispatchTouchEvent(event);
                } else {
                    LogUtils.d("XRefreshLayout", "向上拉");
                    return dispatchTouchEventSupper(event);
                }
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

}
