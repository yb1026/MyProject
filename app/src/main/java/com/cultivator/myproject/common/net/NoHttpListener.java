package com.cultivator.myproject.common.net;

import com.yolanda.nohttp.Response;


public abstract interface NoHttpListener<T> {

    public abstract void onSucceed(int what, Response<T> response);

    public abstract void onFailed(int var1, String var2, Object var3, Exception var4, int var5, long var6);

}