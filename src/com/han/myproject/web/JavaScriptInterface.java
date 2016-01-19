package com.han.myproject.web;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JavaScriptInterface {
	
    private Context contex;
    
    public JavaScriptInterface(Context contex){
    	this.contex = contex;
    }
    
    
    @JavascriptInterface
    public void afterJavaScript(String param){
    	Toast.makeText(contex, param,
				Toast.LENGTH_SHORT).show();
    }
}
