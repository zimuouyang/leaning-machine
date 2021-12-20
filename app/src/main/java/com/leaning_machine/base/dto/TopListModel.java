package com.leaning_machine.base.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopListModel extends BaseDto{
    @Expose
    @SerializedName("userName")
    private String userName;

    @Expose
    @SerializedName("time")
    private Long time;
    @Expose
    @SerializedName("index")
    private int index;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "TopListDto{" +
                "userName='" + userName + '\'' +
                ", time=" + time +
                ", index=" + index +
                '}';
    }
}
