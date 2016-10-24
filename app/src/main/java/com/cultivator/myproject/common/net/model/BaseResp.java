package com.cultivator.myproject.common.net.model;

/**
 * @author: xin.wu
 * @create time: 2016/4/26 14:49
 * @TODO: 描述
 */
public class BaseResp implements java.io.Serializable {
    /* false: 失败    true:   成功 */
    public boolean isSuceed;

    /* JavaBean */
    public Object data;

    /* error msg  */
    public String errMsg;

    public String code;

    /**
     * token丢失 需要重新登录
     */
    public boolean isTokenMiss = false;
}
