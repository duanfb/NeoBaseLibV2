package com.neo.duan.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neo.duan.R;
import com.neo.duan.utils.StringUtils;


/**
 * Author: neo.duan
 * Date: 2017/03/15 14:26
 * Desc: 公共的可点击的item：类似个人中心单条带箭头item
 */
public class ClickItemView extends LinearLayout {

    private static final String TAG_LINE = "tag_line";

    private boolean mTopLineEnable;
    private boolean mBottomLineEnable;
    private int mLeftIconId;
    private CharSequence mLeftText;
    private float mLeftTextSize;
    private int mRightIconId;
    private CharSequence mRightText;
    private float mRightTextSize;
    private int mLeftTextColorId;
    private int mRightTextColorId;

    private View mTopLine;
    private View mBottomLine;

    private ImageView mIvLeft;
    private TextView mTvLeft;
    private ImageView mIvRight;
    private TextView mTvRight;
    private View mViewRedPoint;
    private ViewGroup mContainer; //中间布局容器

    private View mContentView;

    public ClickItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initCustomAttr(context, attrs);
        initView();
    }

    public ClickItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickItemView(Context context) {
        this(context, null);
    }

    private void initCustomAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ClickItem);
        // 获取自定义属性资源ID
        mTopLineEnable = a.getBoolean(R.styleable.ClickItem_topLine, false);
        mBottomLineEnable = a.getBoolean(R.styleable.ClickItem_bottomLine, true);
        mLeftIconId = a.getResourceId(R.styleable.ClickItem_leftIcon, -1);
        mLeftText = a.getText(R.styleable.ClickItem_leftText);
        mLeftTextSize = a.getDimension(R.styleable.ClickItem_leftTextSize, -1);
        mRightIconId = a.getResourceId(R.styleable.ClickItem_rightIcon, R.drawable.ic_common_arrow);
        mRightText = a.getText(R.styleable.ClickItem_rightText);
        mRightTextSize = a.getDimension(R.styleable.ClickItem_rightTextSize, -1);
        mLeftTextColorId = a.getColor(R.styleable.ClickItem_leftTextColor, Color.parseColor("#33383b"));
        mRightTextColorId = a.getColor(R.styleable.ClickItem_rightTextColor, Color.parseColor("#9d9d9d"));
        a.recycle();
    }

    /**
     * 初始化布局信息
     */
    private void initView() {
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);

        //添加顶部线
        addView(mTopLine = createLine());
        //添加中间内容:weight = 1
        View itemView = View.inflate(getContext(), R.layout.layout_click_item_view, null);
        itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        addView(itemView);
        //添加底部线
        addView(mBottomLine = createLine());

        setBackgroundResource(R.color.common_white);

        mIvLeft = (ImageView) findViewById(R.id.iv_item_view_left);
        mTvLeft = (TextView) findViewById(R.id.tv_item_view_left);
        mIvRight = (ImageView) findViewById(R.id.iv_item_view_right);
        mTvRight = (TextView) findViewById(R.id.tv_item_view_right);
        mViewRedPoint = findViewById(R.id.view_item_view_red_point);
        mContainer = (ViewGroup) findViewById(R.id.rl_item_view_container);

        enableTopLine(mTopLineEnable);
        enableBottomLine(mBottomLineEnable);
        setLeftIcon(mLeftIconId);
        setLeftText(String.valueOf(mLeftText));
        setLeftTextSize(mLeftTextSize);
        setRightIcon(mRightIconId);
        setRightText(String.valueOf(mRightText));
        setRightTextSize(mRightTextSize);
        setLeftTextColor(mLeftTextColorId);
        setRightTextColor(mRightTextColorId);
    }

    /**
     * 创建线条
     *
     * @return line
     */
    private View createLine() {
        View lineView = new View(getContext());
        lineView.setTag(TAG_LINE);
        lineView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        lineView.setBackgroundColor(getResources().getColor(R.color.line));
        return lineView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount <= 0) {
            return;
        }
        View lastChildView = getChildAt(childCount - 1);

        if (!TAG_LINE.equals(lastChildView.getTag())) {
            this.removeView(lastChildView);
            setContentView(lastChildView);
        }
    }

    /**
     * 设置左边文本的颜色
     *
     * @param color color
     */
    private void setRightTextColor(int color) {
        mTvRight.setTextColor(color);
    }

    /**
     * 设置右边文本的颜色
     *
     * @param color color
     */
    private void setLeftTextColor(int color) {
        mTvLeft.setTextColor(color);
    }

    /**
     * 左边icon是否可用
     *
     * @param enable enable
     * @param resId resId
     */
    public void enableLeftIcon(boolean enable, int resId) {
        if (enable) {
            mIvLeft.setImageResource(resId);
            mIvLeft.setScaleType(ScaleType.FIT_CENTER);
        }
        mIvLeft.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    public void enableLeftIcon(boolean enable) {
        enableLeftIcon(enable, R.drawable.ic_launcher);
    }

    /**
     * 设置左边图片
     *
     * @param resId resId
     */
    public void setLeftIcon(int resId) {
        if (resId < 0) {
            enableLeftIcon(false);
            return;
        }
        enableLeftIcon(true, resId);
    }

    /**
     * 设置左边文本
     *
     * @param text text
     */
    public void setLeftText(String text) {
        if (StringUtils.isBlank(text) || "null".equals(text)) {
            text = "";
        }
        mTvLeft.setText(text);
    }

    public void setLeftTextSize(float size) {
        if (size < 0) {
            return;
        }
        mTvLeft.setTextSize(size);
    }

    public void setLeftText(int resId) {
        if (resId < 0) {
            mTvLeft.setText("");
            return;
        }
        mTvLeft.setText(getResources().getString(resId));
    }

    /**
     * 设置顶部线条是否可用
     *
     * @param enable enable
     */
    public void enableTopLine(boolean enable) {
        mTopLine.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置底部线条是否可用
     *
     * @param enable enable
     */
    public void enableBottomLine(boolean enable) {
        mBottomLine.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置是否显示小红点
     *
     * @param enable enable
     */
    public void enableRedPoint(boolean enable) {
        mViewRedPoint.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    /**
     * 右边icon是否可用
     *
     * @param enable enable
     * @param resId resId
     */
    public void enableRightIcon(boolean enable, int resId) {
        if (enable) {
            if (resId > 0) {
                mIvRight.setImageResource(resId);
            }
        }
        mIvRight.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    public void enableRightIcon(boolean enable) {
        enableRightIcon(enable, R.drawable.ic_launcher);
    }

    /**
     * 设置右边图标
     *
     * @param resId resId
     */
    public void setRightIcon(int resId) {
        if (resId < 0) {
            enableRightIcon(false);
            return;
        }
        mIvRight.setImageResource(resId);
    }

    /**
     * 设置右边文本
     *
     * @param resId resId
     */
    public void setRightText(int resId) {
        if (resId < 0) {
            mTvRight.setText("");
            return;
        }
        mTvRight.setText(getResources().getString(resId));
    }

    /**
     * 设置右边文本
     */
    public void setRightText(String text) {
        if (StringUtils.isEmpty(text) || "null".equals(text)) {
            text = "";
        }
        mTvRight.setText(text);
    }

    public void setRightTextSize(float size) {
        if (size < 0) {
            return;
        }
        mTvRight.setTextSize(size);
    }

    /**
     * 设置中间容器view
     *
     * @param layoutResID 资源id
     */
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(getContext(), layoutResID, null));
    }

    /**
     * 设置中间容器view
     *
     * @param contentView view
     */
    public void setContentView(View contentView) {
        this.mContentView = contentView;
        mContainer.removeAllViews();
        mContainer.addView(contentView);
    }

    /**
     * 获取中间容器填充的View
     *
     * @return contentView
     */
    public View getContentView() {
        return mContentView;
    }
}
