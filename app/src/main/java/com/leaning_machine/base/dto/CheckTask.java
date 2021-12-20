package com.leaning_machine.base.dto;

import com.bumptech.glide.load.engine.Resource;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class CheckTask implements Serializable {
    @Expose
    @SerializedName("recordDate")
    private Date recordDate;

    @Expose
    @SerializedName("des")
    private String des;

    @Expose
    @SerializedName("taskId")
    private Long taskId;

    @Expose
    @SerializedName("recorded")
    private boolean recorded;

    @Expose
    @SerializedName("resource")
    private ResourceDto resource;

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public ResourceDto getResource() {
        return resource;
    }

    public void setResource(ResourceDto resource) {
        this.resource = resource;
    }

    public boolean isRecorded() {
        return recorded;
    }

    public void setRecorded(boolean recorded) {
        this.recorded = recorded;
    }
}
