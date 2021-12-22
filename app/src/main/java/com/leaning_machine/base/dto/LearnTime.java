package com.leaning_machine.base.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class LearnTime implements Serializable {


    @Expose
    @SerializedName("total")
    private Long total = 0L;

    @Expose
    @SerializedName("spelling")
    private Long spelling = 0L;

    @Expose
    @SerializedName("grindEars")
    private Long grindEars = 0L;

    @Expose
    @SerializedName("fluent")
    private Long fluent = 0L;

    @Expose
    @SerializedName("loveRead")
    private Long loveRead = 0L;


    @Expose
    @SerializedName("practiceFrequently")
    private Long practiceFrequently = 0L;

    @Expose
    @SerializedName("reciteWords")
    private Long reciteWords = 0L;

    @Expose
    @SerializedName("language")
    private Long language = 0L;

    @Expose
    @SerializedName("math")
    private long math = 0L;

    @Expose
    @SerializedName("others")
    private long others = 0L;

    @Expose
    @SerializedName("createDate")
    private String createDate;

    @Expose
    @SerializedName("userId")
    private long userId;


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getSpelling() {
        return spelling;
    }

    public void setSpelling(Long spelling) {
        this.spelling = spelling;
    }

    public Long getGrindEars() {
        return grindEars;
    }

    public void setGrindEars(Long grindEars) {
        this.grindEars = grindEars;
    }

    public Long getFluent() {
        return fluent;
    }

    public void setFluent(Long fluent) {
        this.fluent = fluent;
    }

    public Long getLoveRead() {
        return loveRead;
    }

    public void setLoveRead(Long loveRead) {
        this.loveRead = loveRead;
    }

    public Long getPracticeFrequently() {
        return practiceFrequently;
    }

    public void setPracticeFrequently(Long practiceFrequently) {
        this.practiceFrequently = practiceFrequently;
    }

    public Long getReciteWords() {
        return reciteWords;
    }

    public void setReciteWords(Long reciteWords) {
        this.reciteWords = reciteWords;
    }

    public Long getLanguage() {
        return language;
    }

    public void setLanguage(Long language) {
        this.language = language;
    }

    public Long getMath() {
        return math;
    }

    public void setMath(Long math) {
        this.math = math;
    }

    public Long getOthers() {
        return others;
    }

    public void setOthers(Long others) {
        this.others = others;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
