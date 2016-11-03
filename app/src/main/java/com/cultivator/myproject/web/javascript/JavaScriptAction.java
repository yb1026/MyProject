package com.cultivator.myproject.web.javascript;

import android.webkit.WebView;

import com.cultivator.myproject.common.log.MyLog;
import com.cultivator.myproject.common.util.Utils;

/**
 * Created by yb1026 on 2016/11/3.
 */
public abstract class JavaScriptAction {


    private JavaScriptCallBack javaScriptCallBack;

    protected int reqId = 0;

    public void setJavaScriptInterface(JavaScriptCallBack javaScriptCallBack) {
        this.javaScriptCallBack = javaScriptCallBack;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public abstract void excute(Object param);

    protected void actionCallback(final boolean isSuccess, final String retJson) {
        if (javaScriptCallBack != null) {
            if(isSuccess){
                javaScriptCallBack.actionSuccess(reqId,retJson);
            }else{
                javaScriptCallBack.actionFail(reqId,retJson);
            }
        }
    }

    protected void actionCallback(final boolean isSuccess) {
        if (javaScriptCallBack != null) {
            if(isSuccess){
                javaScriptCallBack.actionSuccess(reqId);
            }else{
                javaScriptCallBack.actionFail(reqId);
            }
        }
    }


}
