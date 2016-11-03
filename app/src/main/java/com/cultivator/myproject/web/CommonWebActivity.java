package com.cultivator.myproject.web;


import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

import com.cultivator.myproject.R;
import com.cultivator.myproject.common.base.BaseActivity;
import com.cultivator.myproject.common.config.IntentConfig;
import com.cultivator.myproject.common.log.MyLog;
import com.cultivator.myproject.common.net.model.BaseResp;
import com.cultivator.myproject.common.util.Utils;
import com.cultivator.myproject.common.view.RefreshWebView;
import com.cultivator.myproject.web.javascript.EndPoint;
import com.cultivator.myproject.web.javascript.JavaScriptActionProvider;
import com.cultivator.myproject.web.javascript.JavaScriptInterface;
import com.cultivator.myproject.web.javascript.Request;
import com.cultivator.myproject.web.javascript.action.RequestLocationAction;
import com.cultivator.myproject.web.javascript.action.RequestUserInfoAction;
import com.cultivator.myproject.web.javascript.action.SendInvitationAction;

import java.io.IOException;
import java.io.InputStream;


/**
 * 支持银行列表
 *
 * @author:yb1026
 */
public class CommonWebActivity extends BaseActivity {

    private RefreshWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.com_refresh_webview);
        initToolBar();
        webView = (RefreshWebView) findViewById(R.id.mRefreshWebView);


        //final String url = getIntent().getStringExtra(IntentConfig.VALUE);
        final String url = "http://172.19.82.59:8080/invitationRule.html";
        webView.loadUrl(url);


        JavaScriptActionProvider.getIstance().putJavaScriptAction(EndPoint.SendInvitation, new SendInvitationAction(this));
        JavaScriptActionProvider.getIstance().putJavaScriptAction(EndPoint.Location, new RequestLocationAction(this));
        JavaScriptActionProvider.getIstance().putJavaScriptAction(EndPoint.User, new RequestUserInfoAction());

        webView.getWebView().addJavascriptInterface(new JavaScriptInterface(webView.getWebView()), "bridgeHandler");



        webView.setLoadListener(new RefreshWebView.LoadListener() {
            @Override
            public void onload(WebView view, String url) {

            }

            @Override
            public void onfinish(WebView view) {
                loadscript();
            }
        });


        if (!Utils.isNull(getIntent().getStringExtra("share"))) {
            getToolBar().setImageRightIcon(R.mipmap.share);
            getToolBar().setRightActionBarOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.shareMsg(CommonWebActivity.this, "分享到", url);
                }
            });
        }
    }


    private void initToolBar() {
        if (Utils.isNull(getIntent().getStringExtra(IntentConfig.ACTIVITY_TITLE))) {
            getToolBar().setBarVisibility(false);
        } else {
            getToolBar().setTitle(getIntent().getStringExtra(IntentConfig.ACTIVITY_TITLE));
        }
    }


    private void loadscript() {

        MyLog.e("loadscript start");

        InputStream in = null;
        try {
            in = getAssets().open("bridge.js");

            byte[] buffer = new byte[in.available()];

            in.read(buffer);


            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);

            webView.getWebView().loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var script = document.createElement('script');" +
                    "script.type = 'text/javascript';" +
                    "script.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(script);" +
                    "})()");

            MyLog.e("loadscript end");
        } catch (IOException e) {
            MyLog.e("loadscript error",e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {

            }
        }


    }


    @Override
    public void onBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            this.finish();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSucceed(int what, BaseResp result) {

    }

    @Override
    public void onFailed(int what, String errorMsg) {

    }
}
