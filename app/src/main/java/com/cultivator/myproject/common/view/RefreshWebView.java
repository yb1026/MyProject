package com.cultivator.myproject.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.cultivator.myproject.R;
import com.cultivator.myproject.common.log.MyLog;
import com.cultivator.myproject.common.util.ToastUtil;


/**
 * @author: xin.wu
 * @create time: 2016/10/19 11:29
 * @TODO: 可下拉刷新的webview
 */
public class RefreshWebView extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {
    private WebView webView;
    private ProgressBar progressbar;
    private Context mContext;
    private View rootView;
    private String loadedUrl;
    private LoadListener loadListener;

    public RefreshWebView(Context context) {
        this(context, null);
    }

    public RefreshWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public WebView getWebView() {
        return this.webView;
    }

    public void setLoadListener(LoadListener loadListener) {
        this.loadListener = loadListener;
    }


    private void init() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.view_webview, null);
        addView(rootView);
        webView = (WebView) this.viewById(R.id.webview);
        progressbar = (ProgressBar) this.viewById(R.id.some_progress);
        setOnRefreshListener(this);
        initWebView();
    }

    private void initWebView() {
        initListeners();
        webView.requestFocus();
        webView.setWebContentsDebuggingEnabled(true);
        initWebViewSettings();
        //webView.addJavascriptInterface(new JavaScriptInterface(mContext), "JavaScriptInterface");
        initWebViewClient();
        initWebChromeClient();
    }

    private void initListeners() {
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        getViewTreeObserver().addOnScrollChangedListener(
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (webView.getScrollY() == 0)
                            setEnabled(true);
                        else
                            setEnabled(false);
                    }
                });
    }

    public void loadUrl(String url) {
        this.loadedUrl = url;
        webView.loadUrl(url);
    }

    private void initWebViewSettings() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); // 是否支持JavaScript
        settings.setAllowFileAccess(true);// 允许系统文件访问(不包括res和assets)
        //缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true); // 设置是否支持缩放
        settings.setTextZoom(100);//放大
        settings.setDisplayZoomControls(false);//缩放图标
        //加载默认显示屏幕自适应
        settings.setLoadWithOverviewMode(false);//超屏
        settings.setUseWideViewPort(false);//使用是否启用支持视窗meta标记
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 把所有内容放到WebView组件等宽的一列中
        settings.setDefaultTextEncodingName("utf-8");
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    public boolean canGoBack() {
        return webView.canGoBack();
    }

    public void goBack() {
        webView.goBack();
    }


    private void initWebViewClient() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.indexOf("tel:") < 0) {// 页面上有数字会导致连接电话
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (loadListener != null) {
                    loadListener.onload(view,url);
                }
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);
                if (loadListener != null) {
                    loadListener.onfinish(webView);
                }

                // 页面加载完成后调用onloads
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           android.webkit.SslErrorHandler handler,
                                           android.net.http.SslError error) {
                handler.proceed(); // 信任所有的证书 默认是handler.cancle(),即不做处理
            }
        });
    }


    private void initWebChromeClient() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    viewById(R.id.view_divider).setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                } else {
                    if (progressbar.getVisibility() == View.GONE) {
                        progressbar.setVisibility(View.VISIBLE);
                    }
                    progressbar.setProgress(newProgress);
                }

                super.onProgressChanged(view, newProgress);
            }


            @Override
            public boolean onConsoleMessage(ConsoleMessage cm) {
                // 控制台信息
                MyLog.e("ConsoleMessage:"  +  cm.messageLevel()+"  "+cm.message()+"  "+cm.lineNumber());
                return true;
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     android.webkit.JsResult result) {
                ToastUtil.show(mContext, message);
                result.confirm();
                return true;
            }
        });
    }

    public View viewById(int resId) {
        return rootView.findViewById(resId);
    }

    @Override
    public void onRefresh() {
        viewById(R.id.view_divider).setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        progressbar.setProgress(0);
        webView.reload();
        setRefreshing(false);
    }

    public static interface LoadListener {
        void onload(WebView view, String url);
        void onfinish(WebView view);
    }

}
