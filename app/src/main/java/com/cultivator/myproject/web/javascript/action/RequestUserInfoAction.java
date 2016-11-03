package com.cultivator.myproject.web.javascript.action;

import android.content.Context;
import android.os.Handler;

import com.cultivator.myproject.common.location.MLocation;
import com.cultivator.myproject.common.location.SimpleLocationService;
import com.cultivator.myproject.common.util.JsonUtil;
import com.cultivator.myproject.web.javascript.JavaScriptAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yb1026 on 2016/11/3.
 */
public class RequestUserInfoAction extends JavaScriptAction {

    @Override
    public void excute(Object param) {
        Map<String, String> ret = new HashMap<>();
        ret.put("ret", "15580852371");
        actionCallback(true,JsonUtil.parseObjJson(ret));
    }


}
