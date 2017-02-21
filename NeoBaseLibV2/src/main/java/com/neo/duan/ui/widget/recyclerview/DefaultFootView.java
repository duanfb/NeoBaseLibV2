package com.neo.duan.ui.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neo.duan.R;

/**
 * @author : neo.duan
 * @date : 	 2016/9/15
 * @desc : 默认RecycleView的FootView
 */
public class DefaultFootView extends BaseFooter {

    private TextView mTvLoading;

    public DefaultFootView(Context context) {
        this(context, null);
    }

    public DefaultFootView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.layout_pull_load_view,viewGroup);
    }

    @Override
    public View onViewCreated(View footerView) {
//        mTvLoading = (TextView) footerView.findViewById(R.id.tv_load_more_footer);
        return null;
    }

    @Override
    public void loading() {
        setVisibility(VISIBLE);
//        mTvLoading.setText("正在加载...");
    }

    @Override
    public void loadComplete() {
        setVisibility(GONE);
    }

    @Override
    public void noMore() {
        setVisibility(VISIBLE);
//        mTvLoading.setText("已显示全部内容");
    }
}
