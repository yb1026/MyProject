package com.cultivator.myproject.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cultivator.myproject.common.log.MyLog;
import com.cultivator.myproject.common.util.JsonUtil;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;

import java.nio.charset.Charset;

public class PushDemoReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        for(String key :bundle.keySet()){
            MyLog.e("GetuiSdkDemo    " + "onReceive() bundle     key=" +key + "  value=" + JsonUtil.parseObjJson(bundle.get(key)));
        }

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {

            case  10008:
                //服务pid key = servicePid
                break;
            case  10002:
                //客户端唯一标识码 key = clientid
                break;
            case  10007:
                //是否在线 key = onlineState
                break;
            case  10001:
                //透传
                String packagename = bundle.getString("packagename");
                String appid = bundle.getString("appid");
                String messageid = bundle.getString("messageid");
                String taskid = bundle.getString("taskid");
                String payloadid = bundle.getString("payloadid");
                byte[] payload = bundle.getByteArray("payload");

                if (payload != null) {
                    String data = new String(payload);
                    MyLog.e("GetuiSdkDemo    " + "receiver payload : " + data);

                }
                break;

            default:
                break;
        }
    }







//    case PushConsts.GET_MSG_DATA:
//    // 获取透传数据
//    // String appid = bundle.getString("appid");
//    byte[] payload = bundle.getByteArray("payload");
//
//    String taskid = bundle.getString("taskid");
//    String messageid = bundle.getString("messageid");
//
//    // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
//    boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
//    System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));
//
//    break;
//
//
//
//    case PushConsts.SET_TAG_RESULT:
//    String sn = bundle.getString("sn");
//    String code = bundle.getString("code");
//
//    String text = "设置标签失败, 未知异常";
//    switch (Integer.valueOf(code)) {
//        case PushConsts.SETTAG_SUCCESS:
//            text = "设置标签成功";
//            break;
//
//        case PushConsts.SETTAG_ERROR_COUNT:
//            text = "设置标签失败, tag数量过大, 最大不能超过200个";
//            break;
//
//        case PushConsts.SETTAG_ERROR_FREQUENCY:
//            text = "设置标签失败, 频率过快, 两次间隔应大于1s";
//            break;
//
//        case PushConsts.SETTAG_ERROR_REPEAT:
//            text = "设置标签失败, 标签重复";
//            break;
//
//        case PushConsts.SETTAG_ERROR_UNBIND:
//            text = "设置标签失败, 服务未初始化成功";
//            break;
//
//        case PushConsts.SETTAG_ERROR_EXCEPTION:
//            text = "设置标签失败, 未知异常";
//            break;
//
//        case PushConsts.SETTAG_ERROR_NULL:
//            text = "设置标签失败, tag 为空";
//            break;
//
//        case PushConsts.SETTAG_NOTONLINE:
//            text = "还未登陆成功";
//            break;
//
//        case PushConsts.SETTAG_IN_BLACKLIST:
//            text = "该应用已经在黑名单中,请联系售后支持!";
//            break;
//
//        case PushConsts.SETTAG_NUM_EXCEED:
//            text = "已存 tag 超过限制";
//            break;
//
//        default:
//            break;
//    }
//
//    MyLog.e("GetuiSdkDemo    " + "settag result sn = " + sn + ", code = " + code);
//    MyLog.e("GetuiSdkDemo    " + "settag result sn = " + text);
//    break;
//    case PushConsts.THIRDPART_FEEDBACK:
//                /*
//                 * String appid = bundle.getString("appid"); String taskid =
//                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
//                 * String result = bundle.getString("result"); long timestamp =
//                 * bundle.getLong("timestamp");
//                 *
//                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
//                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
//                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
//                 */
//            break;
}
