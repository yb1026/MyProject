package com.cultivator.myproject.web;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.cultivator.myproject.common.util.JsonUtil;

public class JavaScriptInterface {
	
    private Context contex;
    
    public JavaScriptInterface(Context contex){
    	this.contex = contex;
    }
    
    
    @JavascriptInterface
    public void afterJavaScript(String param){
    	Toast.makeText(contex, JsonUtil.parseObjJson(param),
				Toast.LENGTH_SHORT).show();
    }
}
