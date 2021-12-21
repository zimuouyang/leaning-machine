package com.leaning_machine.base.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class TerminalDetail implements Serializable {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("phone")
    private String phone;
    @Expose
    @SerializedName("createDate")
    private Date createDate;
    @Expose
    @SerializedName("firstLoginDate")
    private Date firstLoginDate;
    @Expose
    @SerializedName("invalidationDate")
    private Date invalidationDate;
    @Expose
    @SerializedName("syncDate")
    private Date syncDate;
    @Expose
    @SerializedName("maxContinuousDay")
    private int maxContinuousDay;
    @Expose
    @SerializedName("currentContinuousDay")
    private int currentContinuousDay;
    @Expose
    @SerializedName("role")
    private int role;
    @Expose
    @SerializedName("terminalSign")
    private String terminalSign;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getFirstLoginDate() {
        return firstLoginDate;
    }

    public void setFirstLoginDate(Date firstLoginDate) {
        this.firstLoginDate = firstLoginDate;
    }

    public Date getInvalidationDate() {
        return invalidationDate;
    }

    public void setInvalidationDate(Date invalidationDate) {
        this.invalidationDate = invalidationDate;
    }

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }

    public int getMaxContinuousDay() {
        return maxContinuousDay;
    }

    public void setMaxContinuousDay(int maxContinuousDay) {
        this.maxContinuousDay = maxContinuousDay;
    }

    public int getCurrentContinuousDay() {
        return currentContinuousDay;
    }

    public void setCurrentContinuousDay(int currentContinuousDay) {
        this.currentContinuousDay = currentContinuousDay;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getTerminalSign() {
        return terminalSign;
    }

    public void setTerminalSign(String terminalSign) {
        this.terminalSign = terminalSign;
    }

    @Override
    public String toString() {
        return "TerminalDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", createDate=" + createDate +
                ", firstLoginDate=" + firstLoginDate +
                ", invalidationDate=" + invalidationDate +
                ", syncDate=" + syncDate +
                ", maxContinuousDay=" + maxContinuousDay +
                ", currentContinuousDay=" + currentContinuousDay +
                ", role=" + role +
                '}';
    }
}
