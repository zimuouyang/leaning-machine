package com.leaning_machine.model;

import java.io.Serializable;

public class App implements Serializable {
    private String imgUrl;
    private boolean remote;
    private String detail;
    private int drawableId;
    private String name;
    private String packageName;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public App(int drawableId) {
        this.drawableId = drawableId;
    }

    public App(int drawableId, String name) {
        this.drawableId = drawableId;
        this.name = name;
    }

    public App(int drawableId, String name, String packageName) {
        this.drawableId = drawableId;
        this.name = name;
        this.packageName = packageName;
    }
}
