package com.leaning_machine.model;

import java.io.Serializable;

public class TodayUse implements Serializable {
    private long useTime;

    private String dateTime;

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
