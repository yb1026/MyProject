package com.cultivator.myproject.web.javascript;

import android.webkit.WebView;

import com.cultivator.myproject.common.log.MyLog;
import com.cultivator.myproject.common.util.Utils;

/**
 * Created by yb1026 on 2016/11/3.
 */
public class JavaScriptCallBack {

    private static final int SUCCESS = 0;
    private static final int ERROR = 1;


    private WebView webView;

    public WebView getWebView() {
        return webView;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    private void actionCallback(final int reqId,final int result,final String retJson){
        webView.post(new Runnable() {
            @Override
            public void run() {
                String cmd = "";

                if (Utils.isNotBlank(retJson)) {
                    cmd = "javascript:bridge.bridge.onNativeCallback(" + reqId + "," + result + "," + retJson + ")";
                } else {
                    cmd = "javascript:bridge.bridge.onNativeCallback(" + reqId + "," + result + ")";
                }

                MyLog.e("js callback cmd=   " + cmd);
                webView.loadUrl(cmd);
            }
        });
    };

    public void actionSuccess(final int reqId,final String retJson){
        actionCallback(reqId, SUCCESS, retJson);
    };


    public void actionSuccess(final int reqId){
        actionSuccess(reqId,null);
    };


    public void actionFail(final int reqId,final String retJson){
        actionCallback(reqId, ERROR, retJson);
    };

    public void actionFail(final int reqId){
        actionFail(reqId, null);
    };
}
