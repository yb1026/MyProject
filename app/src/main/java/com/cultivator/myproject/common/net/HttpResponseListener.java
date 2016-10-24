/*
 * Copyright © YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cultivator.myproject.common.net;

import android.content.Context;
import android.content.DialogInterface;


import com.cultivator.myproject.common.base.BaseActivity;
import com.cultivator.myproject.common.base.ViewListener;
import com.cultivator.myproject.common.log.MyLog;
import com.cultivator.myproject.common.net.model.BaseResp;
import com.cultivator.myproject.common.util.Utils;
import com.cultivator.myproject.common.view.dialog.WaitDialog;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.Response;
import com.yolanda.nohttp.error.ClientError;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.UnKnownHostError;

import org.json.JSONObject;

/**
 * Created in Nov 4, 2015 12:02:55 PM.
 *
 * @author YOLANDA;
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {


    private Context mContext;
    /**
     * Dialog.
     */
    private WaitDialog mWaitDialog;
    private Request<?> mRequest;

    private ViewListener viewListener;

    /**
     * 结果回调.
     */
    private HttpListener callback;

    /**
     * 是否显示dialog.
     */
    private boolean isLoading;

    /**
     * @param context      context用来实例化dialog.
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     * @param canCancel    是否允许用户取消请求.
     * @param isLoading    是否显示dialog.
     */
    public HttpResponseListener(Context context, Request<?> request, HttpListener httpCallback, ViewListener mViewListener, boolean canCancel, boolean isLoading) {
        this.mContext = context;
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
        this.viewListener = mViewListener;
        this.callback = httpCallback;
        this.isLoading = isLoading;
    }


    /**
     * 开始请求, 这里显示一个dialog
     */
    @Override
    public void onStart(int what) {
        if (isLoading && mWaitDialog != null && !mWaitDialog.isShowing() && mContext != null) {
            mWaitDialog.show();
        }
    }

    /**
     * 结束请求, 这里关闭dialog.
     */
    @Override
    public void onFinish(int what) {
        if (isLoading && mWaitDialog != null && mWaitDialog.isShowing() && !mContext.isRestricted()) {
            if (mContext instanceof BaseActivity) {
                if (!((BaseActivity) mContext).isFinishing())
                    mWaitDialog.dismiss();
            } else {
                mWaitDialog.dismiss();
            }
            mWaitDialog = null;
            mContext = null;
        }
    }

    /**
     * 成功回调.
     */
    @Override
    public void onSucceed(int what, Response<T> response) {
        installData(what, response);
    }


    /**
     * @param what
     * @param rp
     * @TODO 数据校验
     */
    public void installData(int what, Response<T> rp) {
        if (Utils.isNull(rp.get())) {
            onFailed(what, AppConfig.CONNECTION_SERVER_ERROR_MSG);
            return;
        }
        MyLog.d(HttpResponseListener.class, "response：" + rp.get().toString());
        BaseResp response = new BaseResp();
        Object obj = rp.get();
        try {
            JSONObject reponseObject = new JSONObject(obj.toString());
            if (reponseObject.has("errMsg")) {
                response.errMsg = !Utils.isNull(reponseObject.optString("errMsg")) ? reponseObject.optString("errMsg") : "暂无数据";
            }

            if (!reponseObject.has("code")) {
                onFailed(what, !Utils.isNull(response.errMsg) ? response.errMsg : AppConfig.CONNECTION_SERVER_ERROR_MSG);
                return;
            }

            if (reponseObject.has("data")) {
                response.data = reponseObject.optString("data").trim();
            }

//            if(!Utils.isNull(response.data) && ResponseCodes.RESP_DATA.equalsIgnoreCase(response.data.toString())){
//                onFailed(what,response.errMsg);
//                return;
//            }

            String code = reponseObject.optString("code").trim();
//            if ((code.equalsIgnoreCase(ResponseCodes.RESP_CODE_SUCCESS) || code == ResponseCodes.RESP_CODE_SUCCESS)) {
//                response.isSuceed = true;
//                onSucceed(what, response);
//            } else if ((code.equalsIgnoreCase(ResponseCodes.SESSION_TIMEOUT) || code == ResponseCodes.SESSION_TIMEOUT || code.equalsIgnoreCase(ResponseCodes.SESSION_LOGINEDBYOTHERDEVICE_RELOGIN))) {
//                response.isTokenMiss = true;
//                onSessionMiss();
//            } else if (code.equalsIgnoreCase(ResponseCodes.SESSION_CHANGED_AUTOLOGINAGAIN)) {
//                response.isTokenMiss = true;
//                onAutoLogin();
//
//            } else {
//                onFailed(what, response.errMsg);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            response.isSuceed = false;
            response.errMsg = e.toString();
            onFailed(what, AppConfig.CONNECTION_SERVER_ERROR_MSG);
        }
    }




    /**
     * @param what
     * @param errorMsg
     * @TODO 失败
     */
    private void onFailed(int what, String errorMsg) {
        if (!Utils.isNull(callback)) {
            callback.onFailed(what, errorMsg);
        }
    }

    /**
     * 失败回调.
     */
    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        MyLog.d(HttpResponseListener.class, "onFailed：" + exception);
        String msg = "";
        if (exception instanceof ClientError) {// 客户端错误
            msg = Exceptions.ERROR_400_MSG;
        } else if (exception instanceof ServerError) {// 服务器错误
            msg = Exceptions.ERROR_500_MSG;
        } else if (exception instanceof NetworkError) {// 网络不好
            msg = Exceptions.ERROR_TIMEOUT_MSG;
        } else if (exception instanceof TimeoutError) {// 请求超时
            msg = Exceptions.ERROR_TIMEOUT_MSG;
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            msg = Exceptions.ERROR_NOT_SERVER_MSG;
        } else {
            msg = Exceptions.ERROR_UNKNOWN_MSG;
        }
        onFailed(what, !Utils.isNull(msg) ? msg : exception.getMessage());
    }

}
