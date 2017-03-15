package com.neo.duan.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neo.duan.R;
import com.neo.duan.ui.activity.AppBaseActivity;


/**
 * @author : neo.duan
 * @date : 	 2016/7/25 0025
 * @desc : 顶部公共导航栏Toolbar
 */
public class CommonTitleBarView extends LinearLayout {

    private Context mContext;

    ViewGroup mFlLeftContainer; //存放左导航容器
    ViewGroup mFlTitleContainer; //存放中间导航容器
    ViewGroup mFlRightContainer; //存放右导航容器
    View mViewBottomLine; //底部导航线

    private View mTitleView;    //外部传进来的自定义View


    private OnBackListener mNavLeftListener;
    private OnNavRightListener mNavRightListener;
    private OnTitleListener mTitleListener;

    /**
     * 提供外部自定义的titleView
     *
     * @return
     */
    public View getTitleView() {
        return mTitleView;
    }

    public CommonTitleBarView(Context context) {
        this(context, null);
    }

    public CommonTitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initListener();
    }


    /**
     * 初始化布局
     */
    private void initView() {
        //设置垂直方向
        setOrientation(VERTICAL);
        //设置默认背景色
//        setBackgroundResource(R.color.top_bar_normal);
        View.inflate(mContext, R.layout.layout_top_bar, this);
//        ButterKnife.bind(this);

        if (!(mContext instanceof AppBaseActivity)) {
            throw new IllegalArgumentException("Activity use common title bar must extends BaseActivity");
        }

        mFlLeftContainer = (ViewGroup) findViewById(R.id.fl_top_bar_left_container);
        mFlTitleContainer = (ViewGroup) findViewById(R.id.fl_top_bar_title_container);
        mFlRightContainer = (ViewGroup) findViewById(R.id.fl_top_bar_right_container);
        mViewBottomLine = findViewById(R.id.view_top_bar_bottom_line);



        //设置默认状态
        enableTop(true);
        enableBack(true, R.drawable.ic_top_bar_back);
        enableRightNav(false);
        enableBottomLine(false);
    }

    /**
     * 设置监听器
     */
    private void initListener() {
        mFlLeftContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNavLeft();
            }
        });
        mFlTitleContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickTitle();
            }
        });
        mFlRightContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNavRight();
            }
        });
    }

    /**
     * 是否显示顶部导航
     *
     * @param enabled
     */
    public void enableTop(boolean enabled) {
        this.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }


    /**
     * 返回按钮是否可用
     *
     * @param enabled
     */
    public void enableBack(boolean enabled) {
        mFlLeftContainer.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    /**
     * 返回按钮是否可用
     *
     * @param enabled 是否可用
     * @param resId   资源id
     */
    public void enableBack(boolean enabled, int resId) {
        enableBack(enabled,resId,"");
    }

    public void enableBack(boolean enabled, Drawable drawable) {
        enableBack(enabled, drawable,"");
    }

    /**
     *
     * @param enabled
     * @param resId
     * @param text
     */
    public void enableBack(boolean enabled, int resId, String text) {
        String typeName = getResources().getResourceTypeName(resId);
        if ("mipmap".equals(typeName) || "drawable".equals(typeName)) {
            enableBack(enabled, ContextCompat.getDrawable(mContext, resId), text);
        } else if ("string".equals(typeName)) {
            enableBack(enabled, getResources().getString(resId));
        }

    }

    public void enableBack(boolean enabled, View view) {
        enableBack(enabled);
        if (enabled) {
            mFlLeftContainer.removeAllViews();
            mFlLeftContainer.addView(view);
        }
    }

    /**
     * 返回键显示文本
     *
     * @param enabled
     * @param backNav
     */
    public void enableBack(boolean enabled, String backNav) {
        enableBack(enabled, ContextCompat.getDrawable(mContext, R.drawable.ic_top_bar_back), backNav);
    }

    /**
     * 返回键显示图片
     *
     * @param enabled
     * @param drawable
     * @param text 文本
     */
    public void enableBack(boolean enabled, Drawable drawable, String text) {
        if (enabled) {
            mFlLeftContainer.removeAllViews();
            TextView tvBack = (TextView) View.inflate(mContext, R.layout.layout_top_bar_nav_tv, null);
            if (drawable != null) {
                // 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvBack.setCompoundDrawables(drawable,null,null,null);
            }

            tvBack.setText(text);
            mFlLeftContainer.addView(tvBack);
        }
        enableBack(enabled);
    }


    public void enableTitle(boolean enabled) {
        mFlTitleContainer.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    public void enableTitle(boolean enabled, String title) {
        if (enabled) {
            title = ((title == null || "null".equals(title)) ? "" : title);
//            if (title.length() > 6) {
//                title = title.substring(0, 6) + "...";
//            }
            TextView tvTitle = (TextView) View.inflate(mContext, R.layout.layout_top_bar_nav_title_tv, null);
            tvTitle.setText(title);
            mFlTitleContainer.removeAllViews();
            mFlTitleContainer.addView(tvTitle);
        }
        enableTitle(enabled);
    }

    public void enableTitle(boolean enabled, int resId) {
        if (resId <= 0) {
            throw new IllegalArgumentException("enableTitle resId <= 0");
        }
        if (enabled) {
            String typeName = getResources().getResourceTypeName(resId);
            if ("layout".equals(typeName)) {
                mTitleView = View.inflate(getContext(), resId, null);
                enableTitle(enabled, mTitleView);
            }else if ("string".equals(typeName)) {
                enableTitle(enabled, getResources().getString(resId));
            }
        } else {
            enableTitle(enabled);
        }
    }

    /**
     * 自定义显示标题布局
     *
     * @param enabled
     * @param titleView
     */
    public void enableTitle(boolean enabled, View titleView) {
        if (enabled) {
            this.mTitleView = titleView;
            mFlTitleContainer.removeAllViews();
            mFlTitleContainer.addView(titleView);
        }
        enableTitle(enabled);
    }

    public void enableRightNav(boolean enabled) {
        mFlRightContainer.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    public void enableRightNav(boolean enabled, int resId) {
        String typeName = getResources().getResourceTypeName(resId);
        if ("mipmap".equals(typeName) || "drawable".equals(typeName)) {
            enableRightNav(enabled, ContextCompat.getDrawable(mContext, resId));
        } else if ("string".equals(typeName)) {
            enableRightNav(enabled, getResources().getString(resId));
        }
    }


    public void enableRightNav(boolean enabled, Drawable drawable) {
        if (enabled) {
            mFlRightContainer.removeAllViews();
            ImageView ivRight = (ImageView) View.inflate(mContext, R.layout.layout_top_bar_nav_iv, null);
            ivRight.setImageDrawable(drawable);
            mFlRightContainer.addView(ivRight);
        }
        enableRightNav(enabled);
    }

    public void enableRightNav(boolean enabled, String rightNav) {
        if (enabled) {
            mFlRightContainer.removeAllViews();
            TextView tvRight = (TextView) View.inflate(mContext, R.layout.layout_top_bar_nav_tv, null);
            tvRight.setText(rightNav);
            mFlRightContainer.addView(tvRight);
        }
        enableRightNav(enabled);
    }

    /**
     * TopBar底部导航线
     * @param enabled
     */
    public void enableBottomLine(boolean enabled) {
        mViewBottomLine.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }


    /**
     * 设置左导航事件
     *
     * @param listener
     */
    public void setOnBackListener(OnBackListener listener) {
        this.mNavLeftListener = listener;
    }

    /**
     * 设置右导航事件
     *
     * @param listener
     */
    public void setOnNavRightListener(OnNavRightListener listener) {
        this.mNavRightListener = listener;
    }

    /**
     * 设置标题事件
     *
     * @param listener
     */
    public void setOnTitleListener(OnTitleListener listener) {
        this.mTitleListener = listener;
    }

    /**
     * 执行左导航事件
     */
    public void onClickNavLeft() {
        if (mNavLeftListener != null) {
            mNavLeftListener.onBack();
        }
    }

    /**
     * 执行右导航事件
     */
    public void onClickNavRight() {
        if (mNavRightListener != null) {
            mNavRightListener.onNavRight();
        }
    }

    /**
     * 执行点击标题事件
     */
    public void onClickTitle() {
        if (mTitleListener != null) {
            mTitleListener.onTitle();
        }
    }

    /**
     * 左导航返回点击监听器
     */
    public interface OnBackListener {
        void onBack();
    }

    /**
     * 有导航点击监听器
     */
    public interface OnNavRightListener {
        void onNavRight();
    }


    /**
     * 标题点击监听器
     */
    public interface OnTitleListener {
        void onTitle();
    }
}
