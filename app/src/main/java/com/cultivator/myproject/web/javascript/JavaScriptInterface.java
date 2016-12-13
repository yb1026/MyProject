package com.cultivator.myproject.web.javascript;

import android.content.Context;
import android.support.annotation.Keep;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.cultivator.myproject.common.log.MyLog;
import com.cultivator.myproject.common.util.JsonUtil;
import com.cultivator.myproject.common.util.Utils;

import java.util.HashMap;
import java.util.Map;

@Keep
public class JavaScriptInterface {

    private WebView webView;

    private JavaScriptCallBack javaScriptCallBack;

    public JavaScriptInterface( WebView webView) {
        this.webView = webView;
        if(javaScriptCallBack==null){
            javaScriptCallBack = new JavaScriptCallBack();
            javaScriptCallBack.setWebView(webView);
        }
    }

    @JavascriptInterface
    public synchronized void postMessage(String requestJson) {

        try {

            MyLog.e("requestJson = "+requestJson);

            Request request = JsonUtil.parseJson(requestJson,Request.class);

            MyLog.e("recive js call  "+ request.getEndpoint());

            JavaScriptAction action = JavaScriptActionProvider.getIstance().getJavaScriptAction(request.getEndpoint());
            if(action!=null){
                action.setJavaScriptInterface(javaScriptCallBack);
                action.setReqId(request.getId());
                action.excute(request.getParams());
            }else{
                MyLog.e("jscall error  "  +request.getEndpoint()+" null action");
                javaScriptCallBack.actionFail(request.getId());
            }
        } catch (Exception e) {
            MyLog.e("jscall error",e);
        }
    }








}
