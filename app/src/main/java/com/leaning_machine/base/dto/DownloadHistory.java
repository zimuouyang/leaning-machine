package com.leaning_machine.base.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class DownloadHistory implements Serializable {
    /**
     * 编号
     */
    private Long id;

    /**
     * 应用名称
     */
    @Expose
    @SerializedName("appName")
    private String appName;

    @Expose
    @SerializedName("packageName")
    private String packageName;

    /**
     * Logo 图片地址
     */
    @Expose
    @SerializedName("apkIconFileId")
    private String apkIconFileId;

    /**
     * 下载日期
     */
    @Expose
    @SerializedName("createdDate")
    private Date createdDate;

    /**
     * 描述
     */
    @Expose
    @SerializedName("des")
    private String des;

    /**
     * 描述
     */
    @Expose
    @SerializedName("releaseNote")
    private String releaseNote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getApkIconFileId() {
        return apkIconFileId;
    }

    public void setApkIconFileId(String apkIconFileId) {
        this.apkIconFileId = apkIconFileId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getReleaseNote() {
        return releaseNote;
    }

    public void setReleaseNote(String releaseNote) {
        this.releaseNote = releaseNote;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
