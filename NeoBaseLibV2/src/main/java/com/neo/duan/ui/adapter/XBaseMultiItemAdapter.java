package com.neo.duan.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Author: neo.duan
 * Date: 2017/04/17
 * Desc: 再次封装BaseMultiItemQuickAdapter
 */
public abstract class XBaseMultiItemAdapter<T extends MultiItemEntity>
        extends BaseMultiItemQuickAdapter<T, XBaseViewHolder> {

    public XBaseMultiItemAdapter(Context context) {
        super(null);
        this.mContext = context;
        this.mLayoutResId = getLayoutResId(0);
    }

    public void addData(T t) {
        if (t != null) {
            mData.add(t);
        }
        notifyItemInserted(mData.size() - 1);
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
    public XBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mLayoutResId = getLayoutResId(viewType);
        return super.onCreateViewHolder(parent, viewType);
    }

    protected abstract void convert(XBaseViewHolder holder, T item);

    protected abstract int getLayoutResId(int viewType);
}
