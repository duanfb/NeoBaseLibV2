package com.neo.duan.ui.widget.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;


/**
 * @author : neo.duan
 * @date : 	 2016/10/11
 * @desc : YggImageView的变种，不加载占位图等，用户自己自定义
 */
public class XImageView2 extends XImageView {

    public XImageView2(Context context) {
        this(context, null);
    }

    public XImageView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XImageView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    /**
     * 复写init方法，不设置占位图
     */
    @Override
    protected void init() {
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params != null) {
            mWidth = params.width > 0 ? params.width : mWidth;
            mHeight = params.height > 0 ? params.height : mHeight;
        }
    }
}
