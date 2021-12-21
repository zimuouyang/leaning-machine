package com.leaning_machine.model;

import java.io.Serializable;

public class UsingApp implements Serializable {
    private String packageName;
    private long startTime;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "UsingApp{" +
                "packageName='" + packageName + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
