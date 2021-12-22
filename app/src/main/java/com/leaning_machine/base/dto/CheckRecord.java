package com.leaning_machine.base.dto;

import com.bumptech.glide.load.engine.Resource;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class CheckRecord implements Serializable {

    @Expose
    @SerializedName("recordDate")
    private String recordDate;

    @Expose
    @SerializedName("des")
    private String des;


    @Expose
    @SerializedName("terminalUser")
    private TerminalDetail terminalUser;


    @Expose
    @SerializedName("resource")
    private ResourceDto resource;

    @Expose
    @SerializedName("checkTask")
    private CheckTask checkTask;

//    private static final long serialVersionUID = 1L;


    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public TerminalDetail getTerminalUser() {
        return terminalUser;
    }

    public void setTerminalUser(TerminalDetail terminalUser) {
        this.terminalUser = terminalUser;
    }

    public ResourceDto getResource() {
        return resource;
    }

    public void setResource(ResourceDto resource) {
        this.resource = resource;
    }

    public CheckTask getCheckTask() {
        return checkTask;
    }

    public void setCheckTask(CheckTask checkTask) {
        this.checkTask = checkTask;
    }
}
