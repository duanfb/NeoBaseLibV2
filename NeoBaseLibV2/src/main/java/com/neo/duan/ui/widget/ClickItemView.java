package com.neo.duan.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neo.duan.R;
import com.neo.duan.utils.StringUtils;

/**
 * @author : neo.duan
 * @date : 	 2016/9/15
 * @desc : 公共的可点击的item：例如"发现"页面中类似"扫一扫"的单条item
 */
public class ClickItemView extends LinearLayout {

    private int mLeftIconId;
    private int mLeftTextId;
    private float mLeftTextSize;
    private int mRightIconId;
    private int mRightTextId;
    private float mRightTextSize;
    private int mLeftTextColorId;
    private int mRightTextColorId;

    private ImageView mIvLeft;
    private TextView mTvLeft;
    private ImageView mIvRight;
    private TextView mTvRight;

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
        mLeftIconId = a.getResourceId(R.styleable.ClickItem_leftIcon, -1);
        mLeftTextId = a.getResourceId(R.styleable.ClickItem_leftText, -1);
        mLeftTextSize = a.getDimension(R.styleable.ClickItem_leftTextSize, -1);
        mRightIconId = a.getResourceId(R.styleable.ClickItem_rightIcon, R.drawable.ic_common_arrow);
        mRightTextId = a.getResourceId(R.styleable.ClickItem_rightText, -1);
        mRightTextSize = a.getDimension(R.styleable.ClickItem_rightTextSize, -1);
        mLeftTextColorId = a.getColor(R.styleable.ClickItem_leftTextColor,
                getResources().getColor(R.color.common_black));
        mRightTextColorId = a.getColor(R.styleable.ClickItem_rightTextColor,
                getResources().getColor(R.color.common_black));
        a.recycle();
    }

    /**
     * 初始化布局信息
     */
    private void initView() {
        setGravity(Gravity.CENTER);

        View.inflate(getContext(), R.layout.layout_click_item_view, this);
        setBackgroundResource(R.color.common_white);

        mIvLeft = (ImageView) findViewById(R.id.iv_item_view_left);
        mTvLeft = (TextView) findViewById(R.id.tv_item_view_left);
        mIvRight = (ImageView) findViewById(R.id.iv_item_view_right);
        mTvRight = (TextView) findViewById(R.id.tv_item_view_right);

        setLeftIcon(mLeftIconId);
        setLeftText(mLeftTextId);
        setLeftTextSize(mLeftTextSize);
        setRightIcon(mRightIconId);
        setRightText(mRightTextId);
        setRightTextSize(mRightTextSize);
        setLeftTextColor(mLeftTextColorId);
        setRightTextColor(mRightTextColorId);
    }

    /**
     * 设置左边文本的颜色
     *
     * @param mRightTextColor
     */
    private void setRightTextColor(int mRightTextColor) {
        mTvRight.setTextColor(mRightTextColor);
    }

    /**
     * 设置右边文本的颜色
     *
     * @param mLeftTextColor
     */
    private void setLeftTextColor(int mLeftTextColor) {
        mTvLeft.setTextColor(mLeftTextColor);
    }

    /**
     * 左边icon是否可用
     *
     * @param enable
     * @param resId
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
     * @param resId
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
     * @param text
     */
    public void setLeftText(String text) {
        if (StringUtils.isBlank(text)) {
            text = "";
        }
        mTvLeft.setText(text);
    }

    public void setLeftText(int resId) {
        if (resId < 0) {
            mTvLeft.setText("");
            return;
        }
        mTvLeft.setText(resId);
    }

    public void setLeftTextSize(float size) {
        if (size < 0) {
            return;
        }
        mTvLeft.setTextSize(size);
    }

    /**
     * 右边icon是否可用
     *
     * @param enable
     * @param resId
     */
    public void enableRightIcon(boolean enable, int resId) {
        if (enable) {
            if (resId < 0) {
                // do nothing
            } else {
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
     * @param resId
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
     * @param resId
     */

    public void setRightText(int resId) {
        if (resId < 0) {
            mTvRight.setText("");
            return;
        }
        mTvRight.setText(getResources().getString(resId));
    }

    public void setRightTextSize(float size) {
        if (size < 0) {
            return;
        }
        mTvRight.setTextSize(size);
    }

    /**
     * 设置右边文本
     *
     */
    public void setRightText(String text) {
        if (StringUtils.isEmpty(text)) {
            mTvRight.setText("");
        }
        mTvRight.setText(text);
    }
}
