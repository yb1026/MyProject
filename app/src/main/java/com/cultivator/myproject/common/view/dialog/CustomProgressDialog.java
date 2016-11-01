package com.cultivator.myproject.common.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.cultivator.myproject.R;


/**
 * @author: xin.wu
 * @create time: 2016/3/29 17:59
 * @TODO: dialog
 */
public class CustomProgressDialog extends Dialog {

    private static CustomProgressDialog customProgressDialog = null;
    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    private static ContentLoadingProgressBar clp;

    public static CustomProgressDialog createDialog(Context context) {
        customProgressDialog = new CustomProgressDialog(context, R.style.SimpleDialog);
        customProgressDialog.setCancelable(false);
        customProgressDialog.setCanceledOnTouchOutside(false);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
        customProgressDialog.setContentView(v);
        clp = (ContentLoadingProgressBar)v.findViewById(R.id.progress);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return customProgressDialog;
    }

    @Override
    public void show () {
        clp.show();
        super.show();

    }

    public void onWindowFocusChanged(boolean hasFocus) {

        if (customProgressDialog == null) {
            return;
        }
    }
}
