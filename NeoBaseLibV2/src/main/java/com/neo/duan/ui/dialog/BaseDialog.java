package com.neo.duan.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;


/**
 * Author: neo.duan
 * Date: 2017/02/21
 * Desc: 基础对话框基类
 */
public class BaseDialog {
    private Context mContext;
    public BaseDialog(Context context) {
        this.mContext = context;
        init();
    }

    AlertDialog.Builder builder;
    private void init() {
//        builder = new AlertDialog.Builder(mContext, R.style.MyAlertDialogStyle);
//        builder.setTitle("对话框标题");
//        builder.setMessage("对话框消息");
//        builder.setPositiveButton("确定", null);
////        builder.setNegativeButton("取消", null);
    }

    public void show() {
//        super.show();
        builder.show();
    }
}
