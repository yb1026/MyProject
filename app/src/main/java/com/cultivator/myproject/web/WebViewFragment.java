package com.cultivator.myproject.web;

import android.view.View;
import android.webkit.WebView;

import com.cultivator.myproject.R;
import com.cultivator.myproject.common.base.BaseFragment;
import com.cultivator.myproject.common.log.MyLog;
import com.cultivator.myproject.common.net.model.BaseResp;
import com.cultivator.myproject.common.util.Utils;
import com.cultivator.myproject.common.view.RefreshWebView;


/**
 * @author: xin.wu
 * @create time: 2016/10/19 09:23
 * @TODO: 优惠
 */
public class WebViewFragment extends BaseFragment {
    private String Url;
    private RefreshWebView mRefreshWebView;

    public WebViewFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.com_refresh_webview;
    }

    @Override
    public void initUI() {
        Url = "http://www.baidu.com";
        mRefreshWebView = (RefreshWebView)findViewById(R.id.mRefreshWebView);
        mRefreshWebView.loadUrl(Url);
        initLoadListenr();
    }


    private void initLoadListenr(){
        mRefreshWebView.setLoadListener(new RefreshWebView.LoadListener() {
            @Override
            public void onload(WebView view, String url) {
                MyLog.e(getClass(), "url:" + url);
                if (Url.equalsIgnoreCase(url)) {
                    hideLeftView();
                } else {
                    onShowLeftView();
                }
            }
        });
    }

    private void hideLeftView(){
        getToolBar().hideImageLeftBar();
        getToolBar().setTitleLeft("");
    }

    public boolean canGoBack(){
        return  mRefreshWebView.canGoBack();
    }

    public void goBack(){
        mRefreshWebView.goBack();
    }



    @Override
    public void onStart() {
        super.onStart();
        initToolBar();
    }


    private void onShowLeftView(){
        getToolBar().showImageLeftBar();
        getToolBar().setTitleLeft("");
        getToolBar().setImageLeftIcon(R.mipmap.icon_back);
        getToolBar().setLeftActionBarOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (canGoBack()) {
                    goBack();
                    return;
                }else{
                    hideLeftView();
                }
            }
        });
    }

    private void initToolBar() {
        getToolBar().setBackground(R.color.white);
        getToolBar().setTitleColor(R.color.color_36);
        if (canGoBack()) {
            onShowLeftView();
        } else {
            getToolBar().setTitleLeft("");
            getToolBar().hideImageLeftBar();
        }
        getToolBar().hidetRightButton();
        getToolBar().setTitle("优惠资讯");
        getToolBar().setImageRightIcon(R.mipmap.share);
        getToolBar().setRightActionBarOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.shareMsg(getContext(), "分享到", Url);
            }
        });
    }



    @Override
    public void onSucceed (int what, BaseResp result) {

    }

    @Override
    public void onFailed (int what, String errorMsg) {

    }
}
