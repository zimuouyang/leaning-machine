package com.leaning_machine.base.dto;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class AppDto implements Serializable {
    @Expose
    @SerializedName("id")
    private long id;
    @Expose
    @SerializedName("appName")
    private String appName;
    @Expose
    @SerializedName("platform")
    private String platform;
    @Expose
    @SerializedName("versionCode")
    private int versionCode;
    @Expose
    @SerializedName("versionName")
    private String versionName;
    @Expose
    @SerializedName("apkIconFileId")
    private String apkIconFileId;
    @Expose
    @SerializedName("fileName")
    private String fileName;
    @Expose
    @SerializedName("fileId")
    private String fileId;
    @Expose
    @SerializedName("fileSize")
    private double fileSize;
    @Expose
    @SerializedName("createdDate")
    private Date createdDate;
    @Expose
    @SerializedName("releaseNote")
    private String releaseNote;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setApkIconFileId(String apkIconFileId) {
        this.apkIconFileId = apkIconFileId;
    }

    public String getApkIconFileId() {
        return apkIconFileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setReleaseNote(String releaseNote) {
        this.releaseNote = releaseNote;
    }

    public String getReleaseNote() {
        return releaseNote;
    }

}
