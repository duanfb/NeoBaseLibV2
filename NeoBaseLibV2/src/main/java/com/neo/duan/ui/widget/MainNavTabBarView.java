package com.neo.duan.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neo.duan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: neo.duan
 * Date: 2017/02/18
 * Desc: 自定义底部导航tabBar
 */
public class MainNavTabBarView extends LinearLayout {
    private final String TAG = MainNavTabBarView.class.getSimpleName();

    private String[] mTextArr; // 底部文本数组
    private TypedArray mSelectedArr; // 底部选中图片数组
    private TypedArray mUnSelectedArr; // 底部未选中图片数组

    private int mTextColorOn;
    private int mTextColorOff;

    private List<View> mItemViewList = new ArrayList<>(); // 存放底部每个item集合

    private int mCurrentPosition = 0; // 当前点击的position

    private OnTabSelectedListener mListener;

    public MainNavTabBarView(Context context) {
        super(context);
        initView(context);
    }

    public MainNavTabBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCustomAttr(context, attrs);
        initView(context);
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        mListener = listener;
    }

    private void initView(Context context) {
        // 设置LinearLayout水平
        this.setOrientation(LinearLayout.HORIZONTAL);

        // 底部item的数目以textArray为准
        for (int i = 0; i < mTextArr.length; i++) {
            View itemView = initItem(context, i);
            // 初始化item上的数据
            initItemData(itemView, i);
            // 增加到集合中
            mItemViewList.add(itemView);
            // 填充到整个布局
            this.addView(itemView);
        }
        // 默认第0个选中,其他不选中
        setItemSelected(0);
    }

    /**
     * 初始化自定义属性
     */
    private void initCustomAttr(Context context, AttributeSet attrs) {
        TypedArray a = context
                .obtainStyledAttributes(attrs, R.styleable.TabBar);

        // 获取文本颜色
        mTextColorOn = a.getColor(R.styleable.TabBar_textColorOn,
                ContextCompat.getColor(context, R.color.common_black));
        mTextColorOff = a.getColor(R.styleable.TabBar_textColorOff,
                ContextCompat.getColor(context, R.color.common_black));

        // 获取自定义属性资源ID
        int textArrResId = a.getResourceId(R.styleable.TabBar_defaultText,
                R.array.main_tab_default_name);
        int selectedArrResId = a.getResourceId(
                R.styleable.TabBar_defaultIconSelected,
                R.array.main_tab_default_selected);
        int unSelectedArrResId = a.getResourceId(
                R.styleable.TabBar_defaultIconUnSelected,
                R.array.main_tab_default_unselected);

        // 初始化底部文本数组和图片数组
        mTextArr = getResources().getStringArray(textArrResId);
        mSelectedArr = getResources().obtainTypedArray(selectedArrResId);
        mUnSelectedArr = getResources().obtainTypedArray(unSelectedArrResId);

        a.recycle();
    }

    public String[] getTextArr() {
        return mTextArr;
    }

    public void setTextArr(String[] mTextArr) {
        this.mTextArr = mTextArr;
    }

    /**
     * 初始化每个item
     */
    private View initItem(Context context, int position) {
        // 创建item布局
        View itemView = View.inflate(context, R.layout.layout_mian_tab_item,
                null);
        // 配置item参数
        final LayoutParams params = new LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT);

        params.gravity = Gravity.CENTER;

        itemView.setLayoutParams(params);
        params.weight = 1.0f;
        // 设置item点击事件
        itemView.setTag(position);
        //第4个需要校验登录，其他不需要
        if (position != 4) {
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 点击后显示选中状态并回调
                    Integer position = (Integer) v.getTag();
                    if (mCurrentPosition == position) { // 重复点击无效
                        return;
                    }
                    setItemSelected(position);
                }
            });
        } else {
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 点击后显示选中状态并回调
                    Integer position = (Integer) view.getTag();
                    if (mCurrentPosition == position) { // 重复点击无效
                        return;
                    }
                    //点击了则隐藏小红点
                    View redPointView = view.findViewById(R.id.tv_red_point);
                    if (redPointView.getVisibility() == View.VISIBLE) {
                        redPointView.setVisibility(View.GONE);
                    }
                    setItemSelected(position);
                }
            });
        }

        return itemView;
    }

    /**
     * 初始化item上数据显示
     *
     * @param itemView itemView
     * @param position position
     */
    private void initItemData(View itemView, int position) {
        ImageView ivImg = (ImageView) itemView
                .findViewById(R.id.iv_tab_item_img);
        TextView tvName = (TextView) itemView
                .findViewById(R.id.ctv_tab_item_name);
        // ImageView ivDotImg = (ImageView)
        // itemView.findViewById(R.id.iv_tab_item_dot);

        ivImg.setImageDrawable(mUnSelectedArr.getDrawable(position));
        tvName.setText(mTextArr[position]);
        tvName.setTextColor(mTextColorOn);
        // ivDotImg.setVisibility(View.GONE);
    }

    /**
     * 设置item是否选中
     *
     * @param position position
     */
    public void setItemSelected(int position) {
        this.mCurrentPosition = position; // 标记当前选中
        // 选中项设置选中状态
        // 遍历其他的不选中状态
        for (int i = 0; i < mItemViewList.size(); i++) {
            if (i == position) {
                setItemSelectedState(i);
            } else {
                setItemUnSelectedState(i);
            }
        }
        if (mListener != null) { // 回调方法应该跟着选项走
            mListener.onTabSelected(mCurrentPosition);
        }
    }

    /**
     * 设置某个位置item选中
     *
     * @param position position
     */
    private void setItemSelectedState(int position) {
        View itemView = mItemViewList.get(position);
        ImageView ivImg = (ImageView) itemView
                .findViewById(R.id.iv_tab_item_img);
        TextView tvName = (TextView) itemView
                .findViewById(R.id.ctv_tab_item_name);

        ivImg.setImageDrawable(mSelectedArr.getDrawable(position));
        tvName.setText(mTextArr[position]);
        tvName.setTextColor(mTextColorOn);
//        itemView.setBackgroundColor(mItemBgColorOn);
    }

    /**
     * 设置某个位置item不选中
     *
     * @param position position
     */
    private void setItemUnSelectedState(int position) {
        View itemView = mItemViewList.get(position);
        ImageView ivImg = (ImageView) itemView
                .findViewById(R.id.iv_tab_item_img);
        TextView tvName = (TextView) itemView
                .findViewById(R.id.ctv_tab_item_name);

        tvName.setText(mTextArr[position]);
        ivImg.setImageDrawable(mUnSelectedArr.getDrawable(position));
        tvName.setTextColor(mTextColorOff);
        // itemView.setBackgroundColor(mItemBgColorOff);
    }


    /**
     * 设置小红点是否显示
     *
     * @param position 显示小红点位置
     * @param count    数量
     */
    public void setRedDotDisplay(int position, int count) {
        if (position > mItemViewList.size() - 1) {
            return;
        }
        String countStr;
        if (count <= 0) {
            count = 0;
        }
        if (count > 9) {
            countStr = "9+";
        } else {
            countStr = String.valueOf(count);
        }

        View itemView = mItemViewList.get(position);
        TextView tvRedPoint = (TextView) itemView.findViewById(R.id.tv_red_point);
        tvRedPoint.setText(countStr);
        tvRedPoint.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public void setCurrentPosition(int position) {
        this.mCurrentPosition = position;
    }

    /**
     * 底部item选中监听器
     */
    public interface OnTabSelectedListener {
        void onTabSelected(int position);
    }
}
