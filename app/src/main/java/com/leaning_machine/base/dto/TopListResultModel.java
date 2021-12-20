package com.leaning_machine.base.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TopListResultModel implements Serializable {
    /**
     * 日排行榜
     */
    @Expose
    @SerializedName("dayList")
    private List<TopListModel> dayList;
    @Expose
    @SerializedName("monthList")
    private List<TopListModel> monthList;

    /**
     * 日排行榜
     */
    @Expose
    @SerializedName("totalList")
    private List<TopListModel> totalList;

    public List<TopListModel> getDayList() {
        return dayList;
    }

    public void setDayList(List<TopListModel> dayList) {
        this.dayList = dayList;
    }

    public List<TopListModel> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<TopListModel> monthList) {
        this.monthList = monthList;
    }

    public List<TopListModel> getTotalList() {
        return totalList;
    }

    public void setTotalList(List<TopListModel> totalList) {
        this.totalList = totalList;
    }

    @Override
    public String toString() {
        return "TopListResultModel{" +
                "dayList=" + dayList +
                ", monthList=" + monthList +
                ", totalList=" + totalList +
                '}';
    }
}
