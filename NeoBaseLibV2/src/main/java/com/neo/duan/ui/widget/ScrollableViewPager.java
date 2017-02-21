package com.neo.duan.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScrollableViewPager extends ViewPager {
    boolean isCanScroll = true;

    public ScrollableViewPager(Context paramContext) {
        super(paramContext);
    }

    public ScrollableViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
        if (this.isCanScroll) {
            return super.onInterceptTouchEvent(paramMotionEvent);
        }
        return false;
    }

    public void setScrollable(boolean paramBoolean) {
        this.isCanScroll = paramBoolean;
    }
}
