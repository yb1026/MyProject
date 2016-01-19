package com.han.myproject.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.han.myproject.R;

/**
 * @author han.zhang
 */
public class MyWebViewActivity extends Activity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.webview_layout);
		webView = (WebView) this.findViewById(R.id.webview);
		initWebView();
		webView.loadUrl("file:///android_asset/kaitong.html");
		//webView.loadUrl("file:///android_asset/mmm.html");
		//webView.loadUrl("http://www.baidu.com");
		//webView.loadDataWithBaseURL(null,Html.replaceHtml(), "text/html", "utf-8",null);
	}

	private void initWebView() {
		// 设置滚动图标和焦点
		webView.setVerticalScrollBarEnabled(false);
		webView.setHorizontalScrollBarEnabled(false);
		webView.requestFocus(); // WebView显示焦点
		//settings
		initWebViewSettings();
		// 抛给js调用的接口 例：window.JavaScriptInterface.afterJavaScript("");
		webView.addJavascriptInterface(new JavaScriptInterface(this),
				"JavaScriptInterface");
		// 在webview中显示web页面，但不是在web浏览器
		 initWebViewClient();
		 //加载进度以及控制台警告框等交互动作获取
		 initWebChromeClient();
	
		 
		 
	}
	
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	private void initWebViewSettings(){
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true); // 是否支持JavaScript
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 设置缓存的模式
		settings.setAllowFileAccess(true);// 允许系统文件访问(不包括res和assets)

		//缩放
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true); // 设置是否支持缩放
		settings.setTextZoom(100);//放大
		settings.setDisplayZoomControls(false);//缩放图标
		
		//加载默认显示屏幕自适应
		settings.setLoadWithOverviewMode(false);//超屏
		settings.setUseWideViewPort(false);//使用是否启用支持视窗meta标记
	}
	
	
	private void initWebViewClient(){
		webView.setWebViewClient(new WebViewClient() {
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.indexOf("tel:") < 0) {// 页面上有数字会导致连接电话
					view.loadUrl(url);
				}
				// return true,不执行外部loadurl
				return true;
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (Html.BACKURL.equals(url)) {
					Toast.makeText(MyWebViewActivity.this, "返回结果页",
							Toast.LENGTH_SHORT).show();
					return;
				}
				super.onPageStarted(view, url, favicon);
			}
			
			@Override
			public void onPageFinished(WebView webView, String url) {
				// 页面加载完成后调用onloads
				webView.loadUrl("javascript:onloads();");
			}
			@Override
			public void onReceivedSslError(WebView view,
					android.webkit.SslErrorHandler handler,
					android.net.http.SslError error) {
				// super.onReceivedSslError(view, handler, error);
				handler.proceed(); // 信任所有的证书 默认是handler.cancle(),即不做处理
			}
		});
	}
	private void initWebChromeClient(){
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// newProgress 进度
			}
			@Override
			public boolean onConsoleMessage(ConsoleMessage cm) {
				// 控制台信息
				Log.e("ConsoleMessage",
						cm.message() + " -- From line " + cm.lineNumber()
								+ " of " + cm.sourceId());
				return true;
			}
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					android.webkit.JsResult result) {
				Toast.makeText(MyWebViewActivity.this, message,
						Toast.LENGTH_SHORT).show();
				result.confirm();
				return true;
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK  ) {
			if(webView.canGoBack()){
				back(null);
				//webView.goBack();// 返回前一个网页
			}else{
				back(null);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void back(View v) {
		this.finish();
	}

}
