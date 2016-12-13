package com.cultivator.myproject.web.javascript;

import android.support.annotation.Keep;

/**
 * Created by yb1026 on 2016/11/3.
 */

@Keep
public  class Request {

    private int id;
    private String endpoint;
    private Object params;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }


}
