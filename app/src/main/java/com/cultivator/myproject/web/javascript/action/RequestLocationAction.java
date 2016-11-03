package com.cultivator.myproject.web.javascript.action;

import android.content.Context;
import android.os.Handler;

import com.cultivator.myproject.common.location.MLocation;
import com.cultivator.myproject.common.location.SimpleLocationService;
import com.cultivator.myproject.common.log.MyLog;
import com.cultivator.myproject.common.util.JsonUtil;
import com.cultivator.myproject.common.util.Utils;
import com.cultivator.myproject.web.javascript.JavaScriptAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yb1026 on 2016/11/3.
 */
public class RequestLocationAction extends JavaScriptAction {


    private Context contex;

    private SimpleLocationService simpleLocationService;

    private MLocation mLocation;

    public RequestLocationAction(Context contex) {
        super();
        this.contex = contex;

        if (simpleLocationService == null) {
            simpleLocationService = SimpleLocationService.getInstance();

        }
    }

    @Override
    public void excute(Object param) {

        if (simpleLocationService.hasLocation()) {
            mLocation = simpleLocationService.getLocation();
            actionCallback(true, JsonUtil.parseObjJson(mLocation));
        } else {
            simpleLocationService.requestLocation(contex, new SimpleLocationService.Callback() {
                @Override
                public void callback() {
                    mLocation = simpleLocationService.getLocation();
                    actionCallback(true, JsonUtil.parseObjJson(mLocation));
                }
            });
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<String, String> ret = new HashMap<>();
                ret.put("ret", "获取定位失败");
                actionCallback(false, JsonUtil.parseObjJson(ret));
            }
        },10000l);
    }


}
