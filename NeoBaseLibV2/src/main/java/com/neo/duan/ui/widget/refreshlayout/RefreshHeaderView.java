package com.neo.duan.ui.widget.refreshlayout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Author: neo.duan
 * Date: 2017/05/18 15:56
 * Desc: 下拉刷新Header View
 */
public class RefreshHeaderView extends FrameLayout implements PtrUIHandler {

    private AnimationDrawable animationRefresh;

    private ImageView mIvPerson;
    private TextView mTvState;

    public RefreshHeaderView(@NonNull Context context) {
        this(context, null);
    }

    public RefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        View.inflate(getContext(), R.layout.pull_load_view, this);
//        mTvState = (TextView) this.findViewById(R.id.state_tv);
//        mIvPerson = (ImageView) this.findViewById(R.id.pull_person);
//        mIvPerson.setImageResource(R.drawable.refreshing_anim);
//
//        // 通过ImageView对象拿到背景显示的AnimationDrawable
//        animationRefresh = (AnimationDrawable) mIvPerson.getDrawable();
    }

    /**
     * 重置
     * @param frame
     */
    @Override
    public void onUIReset(PtrFrameLayout frame) {
//        animationRefresh.stop();
    }

    /**
     * 准备刷新
     * @param frame
     */
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        if (frame.isPullToRefresh()) {
//            mTvState.setText("下拉加载");
        }
    }

    /**
     * 刷新中
     * @param frame
     */
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        animationRefresh.start();
//        mTvState.setText("加载中...");
    }

    /**
     * 刷新完成
     * @param frame
     */
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        animationRefresh.start();
//        mTvState.setText("加载成功");
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();

        if (currentPos < mOffsetToRefresh) {
            //未到达刷新线
            if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
//                mTvState.setText("下拉加载");
            }
        } else if (currentPos > mOffsetToRefresh) {
            //到达或超过刷新线
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
//                mTvState.setText("释放开始加载");
            }
        }
    }
}
