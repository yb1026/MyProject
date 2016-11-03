package com.cultivator.myproject.web.javascript;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yb1026 on 2016/11/3.
 */
public class JavaScriptActionProvider {


    private static Map<String, JavaScriptAction> provider;

    private static JavaScriptActionProvider instance;

    private JavaScriptActionProvider() {
    }

    public static synchronized JavaScriptActionProvider getIstance() {

        if (instance == null) {
            instance = new JavaScriptActionProvider();
        }

        return instance;
    }


    public void putJavaScriptAction(String endPoint, JavaScriptAction action) {
        if (provider == null) {
            provider = new HashMap<>();
        }

        provider.put(endPoint, action);
    }


    public JavaScriptAction getJavaScriptAction(String endPoint) {
        if (provider == null) {
            return null;
        }
        return provider.get(endPoint);
    }

    public void removeJavaScriptAction(String endPoint) {
        if (provider == null) {
            return;
        }
        provider.remove(endPoint);
    }

    public void clear() {
        if (provider == null) {
            return;
        }
        provider.clear();
        provider = null;
    }
}