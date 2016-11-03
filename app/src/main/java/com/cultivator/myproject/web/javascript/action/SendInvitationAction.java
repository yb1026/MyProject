package com.cultivator.myproject.web.javascript.action;

import android.content.Context;

import com.cultivator.myproject.common.util.JsonUtil;
import com.cultivator.myproject.common.util.Utils;
import com.cultivator.myproject.web.javascript.JavaScriptAction;
import com.cultivator.myproject.web.javascript.JavaScriptInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yb1026 on 2016/11/3.
 */
public class SendInvitationAction extends JavaScriptAction {


    private Context contex;

    public  SendInvitationAction(Context contex){
        super();
        this.contex = contex;
    }

    @Override
    public void excute(Object param) {

        try {
            Utils.shareMsg(contex, "分享到", String.valueOf(param));
            Map<String, String> ret = new HashMap<>();
            ret.put("ret", "分享成功");
            actionCallback(true,JsonUtil.parseObjJson(ret));
        }catch (Exception e){
            Map<String, String> ret = new HashMap<>();
            ret.put("ret", "分享失败");
            actionCallback(false, JsonUtil.parseObjJson(ret));

        }
    }




}
