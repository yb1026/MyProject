package com.cultivator.myproject.common.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cultivator.myproject.R;
import com.cultivator.myproject.common.util.StringUtil;


/**
 * @author: xin.wu
 * @create time: 2015/12/21 10:54
 * @TODO: 自定义dialog
 */
public class CustomDialogUtil {
    private AlertDialog mCustomAlertDlg;
    private Dialog mCustomDlg;

    public AlertDialog showDialog(Context activity, View contentView, String contentMsg) {
        return showDialog(activity, contentView, contentMsg, "确定", "取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public AlertDialog showDialog(Context activity, View contentView, String contentMsg, String subtext, String canceltext) {
        return showDialog(activity, contentView, contentMsg, subtext, canceltext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public AlertDialog showDialog(Context activity, View contentView, String contentMsg, View.OnClickListener sublistener, View.OnClickListener cancellistner) {
        return showDialog(activity, contentView, contentMsg, "确定", "取消", sublistener, cancellistner);
    }

    public AlertDialog showDialog(Context activity, View contentView, String contentMsg, View.OnClickListener sublistener) {
        return showDialog(activity, contentView, contentMsg, "确定", "取消", sublistener, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public AlertDialog showListDialog(Context activity, String titleMsg, BaseAdapter adapter, final CustomDialogUtil.onListSelectListener onListSelect) {
        mCustomAlertDlg = new AlertDialog.Builder(activity).create();
        final ListView lv = new ListView(activity);
        lv.setAdapter(adapter);
        final CustomDialogUtil cdu = new CustomDialogUtil();
        View dlg_defaultview = View.inflate(activity, R.layout.dialog_default, null);
        FrameLayout dlg_content = (FrameLayout) dlg_defaultview.findViewById(R.id.dlg_content);
        LinearLayout btn_bar = (LinearLayout) dlg_defaultview.findViewById(R.id.btn_bar);
        dlg_content.addView(lv);
        mCustomAlertDlg.setView(dlg_defaultview);
        if (!StringUtil.isEmpty(titleMsg)) {
            ((TextView) dlg_defaultview.findViewById(R.id.dlg_title)).setText(titleMsg);
        } else {
            ((TextView) dlg_defaultview.findViewById(R.id.dlg_title)).setVisibility(View.GONE);
        }

        btn_bar.setVisibility(View.GONE);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListSelect.onSelect(position);
                dismiss();
            }
        });

        return mCustomAlertDlg;
    }

    public Dialog showDialogFromBottom(Context activity, String titleMsg, BaseAdapter adapter, final CustomDialogUtil.onListSelectListener onListSelect) {
        mCustomDlg = new Dialog(activity, R.style.dialog);
        final ListView lv = new ListView(activity);
        lv.setAdapter(adapter);
        WindowManager.LayoutParams localLayoutParams = mCustomDlg.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        localLayoutParams.x = 0;
        localLayoutParams.y = 0;
        localLayoutParams.width = LinearLayout.LayoutParams.FILL_PARENT;

        int screenWidth = ((Activity) activity).getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = ((Activity) activity).getWindowManager().getDefaultDisplay().getHeight();

        if (adapter.getCount() > 10) {
            localLayoutParams.height = screenHeight / 3;
        }
        final CustomDialogUtil cdu = new CustomDialogUtil();
        View dlg_defaultview = View.inflate(activity, R.layout.dialog_default, null);
        FrameLayout dlg_content = (FrameLayout) dlg_defaultview.findViewById(R.id.dlg_content);
        LinearLayout btn_bar = (LinearLayout) dlg_defaultview.findViewById(R.id.btn_bar);
        lv.setLayoutParams(localLayoutParams);
        dlg_content.addView(lv);


        dlg_defaultview.setMinimumWidth(screenWidth);

        mCustomDlg.onWindowAttributesChanged(localLayoutParams);
        mCustomDlg.setCancelable(true);
        mCustomDlg.setCanceledOnTouchOutside(true);
        mCustomDlg.setContentView(dlg_defaultview);

        if (!StringUtil.isEmpty(titleMsg)) {
            ((TextView) dlg_defaultview.findViewById(R.id.dlg_title)).setText(titleMsg);
        } else {
            ((TextView) dlg_defaultview.findViewById(R.id.dlg_title)).setVisibility(View.GONE);
        }

        btn_bar.setVisibility(View.GONE);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListSelect.onSelect(position);
                dismiss();
            }
        });

        return mCustomDlg;
    }

    public interface onListSelectListener {
        void onSelect(int p);
    }

    /**
     * @param activity
     * @param titleMsg       标题
     * @param btnSubMsg      左按钮文本
     * @param btnCanMsg      右按钮文本
     * @param subListener    左按钮事件
     * @param cancelListener 右按钮事件
     * @return Dialog
     */
    public AlertDialog showDialog(Context activity, View contentView, String titleMsg, String btnSubMsg, String btnCanMsg,
                                  final View.OnClickListener subListener,
                                  final View.OnClickListener cancelListener) {

        mCustomAlertDlg = new AlertDialog.Builder(activity).create();
        View dlg_defaultview = View.inflate(activity, R.layout.dialog_default, null);
        FrameLayout dlg_content = (FrameLayout) dlg_defaultview.findViewById(R.id.dlg_content);
        LinearLayout btn_bar = (LinearLayout) dlg_defaultview.findViewById(R.id.btn_bar);
        if (contentView != null) {
            dlg_content.addView(contentView);
        }
        mCustomAlertDlg.setView(dlg_defaultview);
        if (!StringUtil.isEmpty(titleMsg)) {
            ((TextView) dlg_defaultview.findViewById(R.id.dlg_title)).setText(titleMsg);
        } else {
            ((TextView) dlg_defaultview.findViewById(R.id.dlg_title)).setVisibility(View.GONE);
        }

        if (!StringUtil.isEmpty(btnSubMsg)) {
            btn_bar.setVisibility(View.VISIBLE);
            ((Button) dlg_defaultview.findViewById(R.id.btn_sub)).setText(btnSubMsg);
            ((Button) dlg_defaultview.findViewById(R.id.btn_sub)).setOnClickListener(subListener);
        } else {
            btn_bar.setVisibility(View.GONE);
        }

        if (!StringUtil.isEmpty(btnCanMsg)) {
            btn_bar.setVisibility(View.VISIBLE);
            ((Button) dlg_defaultview.findViewById(R.id.btn_cancel)).setText(btnCanMsg);
            ((Button) dlg_defaultview.findViewById(R.id.btn_cancel)).setOnClickListener(cancelListener);
        } else {
            btn_bar.setVisibility(View.GONE);
        }
        return mCustomAlertDlg;
    }

    public AlertDialog showImgDialog(Context activity, View contentView, String titleMsg, String btnSubMsg, String btnCanMsg,
                                     final View.OnClickListener subListener,
                                     final View.OnClickListener cancelListener) {

        mCustomAlertDlg = new AlertDialog.Builder(activity).create();
        View dlg_defaultview = View.inflate(activity, R.layout.dialog_img, null);
        FrameLayout dlg_content = (FrameLayout) dlg_defaultview.findViewById(R.id.dlg_content);
        LinearLayout btn_bar = (LinearLayout) dlg_defaultview.findViewById(R.id.btn_bar);
        if (contentView != null) {
            dlg_content.addView(contentView);
        }
        mCustomAlertDlg.setView(dlg_defaultview);
        if (!StringUtil.isEmpty(titleMsg)) {
            ((TextView) dlg_defaultview.findViewById(R.id.dlg_title)).setText(titleMsg);
        } else {
            ((TextView) dlg_defaultview.findViewById(R.id.dlg_title)).setVisibility(View.GONE);
        }

        if (!StringUtil.isEmpty(btnSubMsg)) {
            btn_bar.setVisibility(View.VISIBLE);
            ((Button) dlg_defaultview.findViewById(R.id.btn_sub)).setText(btnSubMsg);
            ((Button) dlg_defaultview.findViewById(R.id.btn_sub)).setOnClickListener(subListener);
        } else {
            btn_bar.setVisibility(View.GONE);
        }

        if (!StringUtil.isEmpty(btnCanMsg)) {
            btn_bar.setVisibility(View.VISIBLE);
            ((Button) dlg_defaultview.findViewById(R.id.btn_cancel)).setText(btnCanMsg);
            ((Button) dlg_defaultview.findViewById(R.id.btn_cancel)).setOnClickListener(cancelListener);
        } else {
            btn_bar.setVisibility(View.GONE);
        }
        return mCustomAlertDlg;
    }

    public AlertDialog showDialog(Context activity, View contentView, String titleMsg, String btnSubMsg, String btnCanMsg,
                                  final View.OnClickListener subListener,
                                  final View.OnClickListener cancelListener, boolean cancelAble) {

        showDialog(activity, contentView, titleMsg, btnSubMsg, btnCanMsg, subListener, cancelListener);
        mCustomAlertDlg.setCancelable(cancelAble);
        return mCustomAlertDlg;
    }

    public void dismiss() {
        if (mCustomAlertDlg != null && mCustomAlertDlg.isShowing()) {
            mCustomAlertDlg.dismiss();
        }
        if (mCustomDlg != null && mCustomDlg.isShowing()) {
            mCustomDlg.dismiss();
        }
    }

    public void show() {
        if (mCustomAlertDlg != null && !mCustomAlertDlg.isShowing()) {
            mCustomAlertDlg.show();
        }
        if (mCustomDlg != null && !mCustomDlg.isShowing()) {
            mCustomDlg.show();
        }
    }

}
