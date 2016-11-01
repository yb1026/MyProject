package com.cultivator.myproject.web;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.cultivator.myproject.R;
import com.cultivator.myproject.common.base.BaseActivity;
import com.cultivator.myproject.common.config.IntentConfig;
import com.cultivator.myproject.common.net.model.BaseResp;
import com.cultivator.myproject.common.util.Utils;
import com.cultivator.myproject.common.view.RefreshWebView;


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
        webView = (RefreshWebView)findViewById(R.id.mRefreshWebView);


        //final String url = getIntent().getStringExtra(IntentConfig.VALUE);
        final String url = "http://www.baidu.com";
        webView.loadUrl(url);

        if(!Utils.isNull(getIntent().getStringExtra("share"))){
            getToolBar().setImageRightIcon(R.mipmap.share);
            getToolBar().setRightActionBarOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.shareMsg(CommonWebActivity.this,"分享到",url);
                }
            });
        }
    }


    private void initToolBar() {
        if(Utils.isNull(getIntent().getStringExtra(IntentConfig.ACTIVITY_TITLE))){
            getToolBar().setBarVisibility(false);
        }else{
            getToolBar().setTitle(getIntent().getStringExtra(IntentConfig.ACTIVITY_TITLE));
        }
    }


    @Override
    public void onBack() {
       if(webView.canGoBack()){
           webView.goBack();
       }else{
           this.finish();
       }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK  ) {
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
