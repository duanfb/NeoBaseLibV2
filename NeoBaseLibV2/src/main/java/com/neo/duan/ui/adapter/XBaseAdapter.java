package com.neo.duan.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.neo.duan.R;

import java.util.List;

/**
 * Author: neo.duan
 * Date: 2017/02/18
 * Desc: 再次封装BaseViewHolder
 */
public abstract class XBaseAdapter<T> extends BaseQuickAdapter<T, XBaseViewHolder> {
    /**
     * holder.addOnClickListener();需要注意顺序，先add子View的Id，再add父View的id，否则父类会覆盖点击子View事件
     *
     */

    public XBaseAdapter(Context context) {
        super(null);
        this.mContext = context;
        this.mLayoutResId = getLayoutResId(0);

        //设置打开动画并前10个数据不用执行动画
        openLoadAnimation();
        setNotDoAnimationCount(10);
    }

    public void update(List<T> list) {
        setNewData(list);
    }

    public void remove(T t) {
        if (t != null && mData.contains(t)) {
            remove(mData.indexOf(t));
        }
    }

    @Override
    public void loadMoreComplete() {
        super.loadMoreComplete();
        removeAllFooterView();
        addFooterView(View.inflate(mContext, R.layout.layout_no_more, null));
    }

    @Override
    public XBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mLayoutResId = getLayoutResId(viewType);
        return super.onCreateViewHolder(parent, viewType);
    }

    protected abstract int getLayoutResId(int viewType);
}
