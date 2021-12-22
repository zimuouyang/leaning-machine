package com.leaning_machine.model;

import java.io.Serializable;

public class TodayUse implements Serializable {
    private long useTime;

    private String name;

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TodayUse(String name) {
        this.name = name;
    }
}
