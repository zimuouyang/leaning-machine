package com.leaning_machine.base.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopListDto extends BaseDto{
    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("time")
    private Long time;


    @Expose
    @SerializedName("ranking")
    private Long ranking;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getRanking() {
        return ranking;
    }

    public void setRanking(Long ranking) {
        this.ranking = ranking;
    }

    public TopListDto(String name, Long time, Long ranking) {
        this.name = name;
        this.time = time;
        this.ranking = ranking;
    }

    @Override
    public String toString() {
        return "TopListDto{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", ranking=" + ranking +
                '}';
    }
}
