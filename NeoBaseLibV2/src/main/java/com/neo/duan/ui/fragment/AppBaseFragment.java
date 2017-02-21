package com.neo.duan.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neo.duan.R;
import com.neo.duan.manager.ScreenManager;
import com.neo.duan.mvp.present.BasePresenter;
import com.neo.duan.mvp.view.base.IBaseView;
import com.neo.duan.ui.widget.CommonTitleBarView;
import com.neo.duan.utils.EventBusUtil;
import com.neo.duan.utils.ToastUtil;

/**
 * Author: neo.duan
 * Date: 2017/02/21
 * Desc: Fragment基类
 */
public abstract class AppBaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {
    public final String TAG = getClass().getSimpleName();
    public Context mContext;

    public CommonTitleBarView mLlTop; //公共标题栏
    public ViewGroup mContainer; //中间布局容器
    public ViewGroup mTipContainer; //提示布局容器

    public View mContentView;

    public View mLoadingView;
    public View mNetErrorView;

    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_base, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = getActivity();

        initBaseView(view);
        initBaseListeners();

        /***初始化标题栏****/
        initTop();
        /***初始化内容布局****/
        initLayouts();
        /***初始化内容view****/
        initViews();
        /***初始化事件监听****/
        initListeners();
        /***初始化present****/
        mPresenter = initPresents();
        /***初始化数据****/
        initData();
    }

    /**
     * 基类View初始化
     */
    private void initBaseView(View view) {
        mLlTop = (CommonTitleBarView) view.findViewById(R.id.title_id_root);
        mContainer = (ViewGroup) view.findViewById(R.id.content_id_root);
        mTipContainer = (ViewGroup) view.findViewById(R.id.content_tip_root);
    }

    /**
     * 初始化基类事件
     */
    protected void initBaseListeners() {
        mLlTop.setOnBackListener(new CommonTitleBarView.OnBackListener() {

            @Override
            public void onBack() {
                onBackClick();
            }
        });
    }

    /**
     * 默认返回键处理
     */
    private void onBackClick() {
        closeActivity();
    }

    public void setContentView(int layoutResID) {
        setContentView(View.inflate(mContext, layoutResID, null));
    }

    /**
     * 获取内容布局
     *
     * @return
     */
    public View getContentView() {
        return mContentView;
    }

    /**
     * 设置布局文件
     *
     * @param contentView
     */
    public void setContentView(View contentView) {
        this.mContentView = contentView;
        mContainer.addView(mContentView);
    }

    /**
     * 获取顶部标题栏
     *
     * @return
     */
    public CommonTitleBarView getTopBar() {
        return mLlTop;
    }

    /**
     * 是否显示顶部导航
     *
     * @param enabled
     */
    public void enableTop(boolean enabled) {
        mLlTop.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }


    /**
     * 是否显示左导航键
     *
     * @param enabled
     */
    public void enableBack(boolean enabled) {
        mLlTop.enableBack(enabled);
    }

    public void enableBack(boolean enabled, int resId, String text) {
        mLlTop.enableBack(enabled, resId, text);
    }

    public void enableBack(boolean enabled, Drawable drawable) {
        mLlTop.enableBack(enabled, drawable);
    }

    public void enableBack(boolean enabled, int resId) {
        mLlTop.enableBack(enabled, resId);
    }

    public void enableBack(boolean enabled, String navBack) {
        mLlTop.enableBack(enabled, navBack);
    }

    public void enableBack(boolean enabled, View view) {
        mLlTop.enableBack(enabled, view);
    }

    public void enableRightNav(boolean enabled) {
        mLlTop.enableRightNav(enabled);
    }

    public void enableRightNav(boolean enabled, int resId) {
        mLlTop.enableRightNav(enabled, resId);
    }

    public void enableRightNav(boolean enabled, String rightNav) {
        mLlTop.enableRightNav(enabled, rightNav);
    }

    /**
     * 设置标题
     *
     * @param enabled
     * @param title
     */
    public void enableTitle(boolean enabled, String title) {
        mLlTop.enableTitle(enabled, title);
    }

    /**
     * 设置标题
     *
     * @param enabled
     * @param resId
     */
    public void enableTitle(boolean enabled, int resId) {
        mLlTop.enableTitle(enabled, resId);
    }

    /**
     * TopBar底部导航线
     *
     * @param enabled
     */
    public void enableBottomLine(boolean enabled) {
        mLlTop.enableBottomLine(enabled);
    }

    /**
     * 设置顶部标题栏背景颜色
     *
     * @param resId
     */
    public void setTopBarBackground(int resId) {
        if (resId < 0) {
            return;
        }
        mLlTop.setBackgroundColor(getResources().getColor(resId));
    }

    public abstract void initTop();

    /**
     * 初始化视图
     */
    public abstract void initLayouts();

    /**
     * 初始化视图
     */
    public abstract void initViews();

    /**
     * 初始化present
     */
    public abstract P initPresents();

    /**
     * 初始化监听器
     */
    public abstract void initListeners();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 设置左导航事件
     *
     * @param listener
     */
    public void setOnBackListener(CommonTitleBarView.OnBackListener listener) {
        mLlTop.setOnBackListener(listener);
    }

    /**
     * 设置右导航事件
     *
     * @param listener
     */
    public void setOnNavRightListener(CommonTitleBarView.OnNavRightListener listener) {
        mLlTop.setOnNavRightListener(listener);
    }

    /**
     * 设置标题事件
     *
     * @param listener
     */
    public void setOnTitleListener(CommonTitleBarView.OnTitleListener listener) {
        mLlTop.setOnTitleListener(listener);
    }

    /**
     * 关闭activity，相当于销毁
     */
    public void closeActivity() {
        Activity topAct = ScreenManager.getInstance().currentActivity();
        ScreenManager.getInstance().popActivity(topAct);
    }


    @Override
    public void onDestroyView() {
        //取消界面上所有吐司
        ToastUtil.canAll();
        //解绑EventBus
        unRegisterEventBus();
        //销毁Presenter
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }

        super.onDestroyView();
    }


    @Override
    public void showLoading() {
        showLoading("");
    }

    @Override
    public void showLoading(String msg) {
        View topView = mTipContainer.getChildAt(0);
        if (topView != null && topView == mLoadingView) {
            return;
        }
        mTipContainer.removeAllViews();
        if (mLoadingView == null) {
            mLoadingView = View.inflate(mContext, R.layout.layout_loading,null);
        }
        TextView textView = (TextView) mLoadingView.findViewById(R.id.tv_loading_msg);
        textView.setText(msg);
        mTipContainer.addView(mLoadingView);
        mTipContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (mLoadingView != null) {
            mTipContainer.removeView(mLoadingView);
            mTipContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        ToastUtil.show(errorMsg);
    }

    @Override
    public void showNetError() {
        View topView = mTipContainer.getChildAt(0);
        if (topView != null && topView == mNetErrorView) {
            return;
        }
        mTipContainer.removeAllViews();
        if (mNetErrorView == null) {
            mNetErrorView = View.inflate(mContext, R.layout.layout_net_error, null);
        }
        mNetErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry();
            }
        });
        mTipContainer.addView(mNetErrorView);
        mTipContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 显示数据为空View，子类可覆盖
     */
    @Override
    public void showEmptyView() {

    }

    /**
     * 子类可复写，网络错误点击重试事件
     */
    public void retry() {
        initData();
    }

    /**
     * 注册EventBus
     */
    public void registerEventBus() {
        EventBusUtil.register(this);
    }

    /**
     * 注销EventBus
     */
    public void unRegisterEventBus() {
        EventBusUtil.unregister(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int id) {
        return (T) getView().findViewById(id);
    }
}
