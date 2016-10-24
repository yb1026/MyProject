package com.cultivator.myproject.common.net;


import android.content.Context;
import android.content.DialogInterface;

import com.cultivator.myproject.common.view.dialog.WaitDialog;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.Response;

/**
 * Created in Nov 4, 2015 12:02:55 PM
 *
 * @author YOLANDA
 */
public class NoHttpResponseListener<T> implements OnResponseListener<T> {

    /**
     * Dialog
     */
    private WaitDialog mWaitDialog;

    private Request<?> mRequest;

    /**
     * 结果回调
     */
    private NoHttpListener<T> callback;

    /**
     * 是否显示dialog
     */
    private boolean isLoading;

    /**
     * @param context      context用来实例化dialog
     * @param request      请求对象
     * @param httpCallback 回调对象
     * @param canCancel    是否允许用户取消请求
     * @param isLoading    是否显示dialog
     */
    public NoHttpResponseListener(Context context, Request<?> request, NoHttpListener<T> httpCallback, boolean canCancel, boolean isLoading) {
        this.mRequest = request;
        if (context != null && isLoading) {
            mWaitDialog = new WaitDialog(context);
            mWaitDialog.setCancelable(canCancel);
            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mRequest.cancel(true);
                }
            });
        }
        this.callback = httpCallback;
        this.isLoading = isLoading;
    }

    /**
     * 开始请求, 这里显示一个dialog
     */
    @Override
    public void onStart(int what) {
        if (isLoading && mWaitDialog != null && !mWaitDialog.isShowing())
            mWaitDialog.show();
    }

    /**
     * 结束请求, 这里关闭dialog
     */
    @Override
    public void onFinish(int what) {
        if (isLoading && mWaitDialog != null && mWaitDialog.isShowing())
            mWaitDialog.dismiss();
    }

    /**
     * 成功回调
     */
    @Override
    public void onSucceed(int what, Response<T> response) {
        if (callback != null)
            callback.onSucceed(what, response);
    }

    @Override
    public void onFailed (int i, String s, Object o, Exception e, int i1, long l) {
        if (callback != null)
            callback.onFailed(i,s,o,e,i1,l);
    }


}