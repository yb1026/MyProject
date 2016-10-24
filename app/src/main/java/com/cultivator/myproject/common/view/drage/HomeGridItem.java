package com.cultivator.myproject.common.view.drage;

import android.app.Activity;

/**
 * Created by liuyang on 16/4/28.
 */
public class HomeGridItem {
    private int id;
    private int mipmapId;
    private Activity activity;
    private int position;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMipmapId() {
        return mipmapId;
    }

    public void setMipmapId(int mipmapId) {
        this.mipmapId = mipmapId;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
