package com.neo.duan.ui.widget.app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.neo.duan.utils.StringUtils;

/**
 * @author : neo.duan
 * @date : 	 2016/7/25 0025
 * @desc : app应用内使用的TextView,方便日后维护
 */
public class XTextView extends TextView {
    public XTextView(Context context) {
        this(context, null);
    }

    public XTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置显示文本
     *
     * @param text
     */
    public void setText(String text) {
        if (StringUtils.isBlank(text)) {
            super.setText("");
        } else {
            super.setText(text);
        }
    }

    /**
     * 设置显示文本且文字大小
     *
     * @param text 文本
     * @param sp   文字大小
     */
    public void setText(String text, int sp) {
        if (StringUtils.isBlank(text)) {
            super.setText("");
        } else {
            super.setText(text);
        }
        super.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
    }


    /**
     * 设置文本中某个文字段的大小
     *
     * @param text       文本
     * @param startIndex 起始index
     * @param endIndex   结束index
     * @param proportion 倍数
     */
    public void setTextSize(String text, int startIndex, int endIndex, float proportion) {
        if (StringUtils.isBlank(text)) {
            super.setText("");
        } else {
            SpannableString ss1 = new SpannableString(text);
            //proportion代表倍数：2f，原字体大小2倍
            ss1.setSpan(new RelativeSizeSpan(proportion), startIndex, endIndex, 0); // set size
            super.setText(ss1);
        }
    }

    /**
     * 设置某段文本字体颜色
     *
     * @param text
     * @param startIndex
     * @param endIndex
     * @param colorResId
     */
    public void setTextColor(String text, int startIndex, int endIndex, int colorResId) {
        if (StringUtils.isBlank(text)) {
            super.setText("");
        } else {
            SpannableString ss1 = new SpannableString(text);
            ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), colorResId)),
                    startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);// set color
            super.setText(ss1);
        }
    }

    /**
     * 设置某段文本字体颜色
     *
     * @param text
     * @param startIndex
     * @param endIndex
     * @param color 颜色String值
     */
    public void setTextColor(String text, int startIndex, int endIndex, String color) {
        if (StringUtils.isBlank(text)) {
            super.setText("");
        } else {
            SpannableString ss1 = new SpannableString(text);
            ss1.setSpan(new ForegroundColorSpan(Color.parseColor(color)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);// set color
            super.setText(ss1);
        }
    }

    /**
     * 设置文本并添加中划线
     *
     * @param text 文本
     */
    public void setTextWithMiddleLine(String text) {
        TextPaint paint = getPaint();
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中划线加清晰
        setText(text);
    }

    /**
     * 设置文本并添加中划线
     *
     * @param text 文本
     */
    public void setTextWithUnderLine(String text) {
        TextPaint paint = getPaint();
        paint.setFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中划线加清晰
        setText(text);
    }
}
