package com.neo.duan.ui.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author : neo.duan
 * @date : 	 2016/9/15
 * @desc : 封装系统的RecyclerView，方便日后需求变动的维护
 */
public class XRecyclerView extends RecyclerView {
    private static final int TYPE_FOOTER = 10001;
    private Context mContext;

    private boolean loadingMoreEnabled = false;
    private boolean isLoadingData = false;
    private boolean isNoMore = false;

    //adapter没有数据的时候显示,类似于listView的emptyView
    private View mEmptyView;
    private View mFootView;

    private LoadingListener mLoadingListener;

    private final AdapterDataObserver mDataObserver = new DataObserver();
    private WrapAdapter mWrapAdapter;

    public XRecyclerView(Context context) {
        this(context, null);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    /**
     * 初始化设置默认的一些属性
     */
    private void init() {
        // 设置水平布局
        this.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        // 设置item动画
        this.setItemAnimator(new DefaultItemAnimator());

        //设置默认footView
        mFootView = new DefaultFootView(getContext());
        mFootView.setVisibility(GONE);
    }

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        mDataObserver.onChanged();
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public void setLoadingMoreEnabled(boolean enabled) {
        loadingMoreEnabled = enabled;
        if (!enabled) {
            if (mFootView instanceof BaseFooter) {
                ((BaseFooter) mFootView).setState(BaseFooter.STATE_COMPLETE);
            }
        }
    }

    /**
     * 判断是否是XRecyclerView保留的itemViewType
     *
     * @param itemViewType
     * @return
     */
    private boolean isReservedItemViewType(int itemViewType) {
        return itemViewType == TYPE_FOOTER;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mWrapAdapter = new WrapAdapter(adapter);
        super.setAdapter(mWrapAdapter);
        adapter.registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
    }

    /**
     * 对外提供基于BaseFooter的View
     *
     * @param view
     */
    public void setFootView(BaseFooter view) {
        mFootView = view;
    }

    /**
     * 对外提供设置自定义的View
     *
     * @param view
     */
    public void setFootView(View view) {
        mFootView = view;
        mFootView.setVisibility(GONE);
    }

    public void loadMoreComplete() {
        isLoadingData = false;
        mWrapAdapter.setHasFoot(false);
        if (mFootView instanceof BaseFooter) {
            ((BaseFooter) mFootView).setState(BaseFooter.STATE_COMPLETE);
        } else {
            if (mFootView != null) {
                mFootView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setNoMore(boolean noMore) {
        isLoadingData = false;
        isNoMore = noMore;
        if (mFootView instanceof BaseFooter) {
            ((BaseFooter) mFootView).setState(isNoMore ? BaseFooter.STATE_NOMORE : BaseFooter.STATE_COMPLETE);
        } else {
            if (mFootView != null) {
                mFootView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void reset() {
        setNoMore(false);
        loadMoreComplete();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadingListener != null && !isLoadingData && loadingMoreEnabled) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1 && layoutManager.getItemCount() > layoutManager.getChildCount() && !isNoMore) {
                isLoadingData = true;
                mWrapAdapter.setHasFoot(true);
                if (mFootView instanceof BaseFooter) {
                    ((BaseFooter) mFootView).setState(BaseFooter.STATE_LOADING);
                } else {
                    if (mFootView != null) {
                        mFootView.setVisibility(View.VISIBLE);
                    }
                }
                mLoadingListener.onLoadMore();
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


    private class DataObserver extends AdapterDataObserver {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && mEmptyView != null) {
                int emptyCount = 0;
                if (mWrapAdapter != null && mWrapAdapter.hasFoot) {
                    emptyCount++;
                }
                if (adapter.getItemCount() == emptyCount) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    XRecyclerView.this.setVisibility(View.GONE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                    XRecyclerView.this.setVisibility(View.VISIBLE);
                }
            }
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    }

    /**
     * Adapter包装类：添加滑动到底部加载更多
     */
    public class WrapAdapter extends Adapter<ViewHolder> {

        private Adapter adapter;
        private boolean hasFoot;

        public WrapAdapter(Adapter adapter) {
            this.adapter = adapter;
        }

        /**
         * 设置是否有 FooterView
         *
         * @param hasFoot true 为有，false 为无
         */
        public void setHasFoot(boolean hasFoot) {
            if (this.hasFoot != hasFoot) {
                this.hasFoot = hasFoot;
                if (hasFoot) {
                    adapter.notifyItemInserted(adapter.getItemCount());
                }
            }
        }

        public boolean hasFoot() {
            return hasFoot;
        }

        public boolean isFooter(int position) {
            if (hasFoot) {
                return position == getItemCount() - 1;
            } else {
                return false;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_FOOTER) {
                return new SimpleViewHolder(mFootView);
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if (loadingMoreEnabled) {
                if (isFooter(position)) {
                   // do nothing
                } else {
                    adapter.onBindViewHolder(holder, position);
                }
            } else {
                adapter.onBindViewHolder(holder, position);
            }
        }

        @Override
        public int getItemCount() {
            if (hasFoot && loadingMoreEnabled) {
                if (adapter != null) {
                    return adapter.getItemCount() + 1;
                } else {
                    return 0;
                }
            } else {
                if (adapter != null) {
                    return adapter.getItemCount();
                } else {
                    return 0;
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (hasFoot && isFooter(position)) {
                return TYPE_FOOTER;
            }
//            if (isReservedItemViewType(adapter.getItemViewType(adjPosition))) {
//                throw new IllegalStateException("XRecyclerView require itemViewType in adapter should be less than 10000 ");
//            }
            if (adapter != null) {
                return adapter.getItemViewType(position);
            }
            return -1;
        }

        @Override
        public long getItemId(int position) {
            if (hasFoot && adapter != null) {
                if (isFooter(position)) {
                    return -1;
                }
                return adapter.getItemId(position);
            } else {
                if (adapter != null) {
                    return adapter.getItemId(position);
                }
            }
            return -1;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridManager.getSpanSizeLookup();
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (isFooter(position)) {
                            return gridManager.getSpanCount();
                        } else {
                            return spanSizeLookup.getSpanSize(position);
                        }
                    }
                });
            }
            adapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            adapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && isFooter(holder.getLayoutPosition())) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
            adapter.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            adapter.onViewDetachedFromWindow(holder);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            adapter.onViewRecycled(holder);
        }

        @Override
        public boolean onFailedToRecycleView(ViewHolder holder) {
            return adapter.onFailedToRecycleView(holder);
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            adapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            adapter.registerAdapterDataObserver(observer);
        }

        private class SimpleViewHolder extends ViewHolder {
            public SimpleViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }

    public interface LoadingListener {
        void onLoadMore();
    }
}
