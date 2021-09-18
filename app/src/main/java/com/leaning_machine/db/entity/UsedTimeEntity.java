package com.leaning_machine.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 *
 * @author John
 * @date 2021/9/16
 */
@Entity(tableName = "USED_TIME")
public class UsedTimeEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long id;

    @NonNull
    @ColumnInfo(name = "USED_DATE")
    private String date;

    @NonNull
    @ColumnInfo(name = "TOTAL_LENGTH")
    private long totalLength;

    @NonNull
    @ColumnInfo(name = "PIN_DU_LENGTH")
    private long pinDuLength;

    @NonNull
    @ColumnInfo(name = "ER_DUO_LENGTH")
    private long erDuoLength;

    @NonNull
    @ColumnInfo(name = "LIU_LI_LENGTH")
    private long liuLiLength;

    @NonNull
    @ColumnInfo(name = "YUE_DU_LENGTH")
    private long yueDuLength;

    @NonNull
    @ColumnInfo(name = "LIAN_XI_LENGTH")
    private long lianXiLength;

    @NonNull
    @ColumnInfo(name = "DAN_CI_LENGTH")
    private long danCiLength;

    @NonNull
    @ColumnInfo(name = "SEE_MOVIE_LENGTH")
    private long kanDianYingLength;

    @NonNull
    @ColumnInfo(name = "QU_YI_ZHI_LENGTH")
    private long quLeZhiLength;

    @NonNull
    @ColumnInfo(name = "LANGUAGE_LENGTH")
    private long languageLength;

    @NonNull
    @ColumnInfo(name = "MATH_LENGTH")
    private long mathLength;

    @NonNull
    @ColumnInfo(name = "OTHER_LENGTH")
    private long otherLength;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public long getPinDuLength() {
        return pinDuLength;
    }

    public void setPinDuLength(long pinDuLength) {
        this.pinDuLength = pinDuLength;
    }

    public long getErDuoLength() {
        return erDuoLength;
    }

    public void setErDuoLength(long erDuoLength) {
        this.erDuoLength = erDuoLength;
    }

    public long getLiuLiLength() {
        return liuLiLength;
    }

    public void setLiuLiLength(long liuLiLength) {
        this.liuLiLength = liuLiLength;
    }

    public long getYueDuLength() {
        return yueDuLength;
    }

    public void setYueDuLength(long yueDuLength) {
        this.yueDuLength = yueDuLength;
    }

    public long getLianXiLength() {
        return lianXiLength;
    }

    public void setLianXiLength(long lianXiLength) {
        this.lianXiLength = lianXiLength;
    }

    public long getDanCiLength() {
        return danCiLength;
    }

    public void setDanCiLength(long danCiLength) {
        this.danCiLength = danCiLength;
    }

    public long getLanguageLength() {
        return languageLength;
    }

    public void setLanguageLength(long languageLength) {
        this.languageLength = languageLength;
    }

    public long getMathLength() {
        return mathLength;
    }

    public void setMathLength(long mathLength) {
        this.mathLength = mathLength;
    }

    public long getOtherLength() {
        return otherLength;
    }

    public void setOtherLength(long otherLength) {
        this.otherLength = otherLength;
    }

    public long getKanDianYingLength() {
        return kanDianYingLength;
    }

    public void setKanDianYingLength(long kanDianYingLength) {
        this.kanDianYingLength = kanDianYingLength;
    }

    public long getQuLeZhiLength() {
        return quLeZhiLength;
    }

    public void setQuLeZhiLength(long quLeZhiLength) {
        this.quLeZhiLength = quLeZhiLength;
    }

    @Override
    public String toString() {
        return "UsedTimeEntity{" +
                "id=" + id +
                ", date=" + date +
                ", totalLength=" + totalLength +
                ", pinDuLength=" + pinDuLength +
                ", erDuoLength=" + erDuoLength +
                ", liuLiLength=" + liuLiLength +
                ", yueDuLength=" + yueDuLength +
                ", lianXiLength=" + lianXiLength +
                ", danCiLength=" + danCiLength +
                ", kanDianYingLength=" + kanDianYingLength +
                ", quLeZhiLength=" + quLeZhiLength +
                ", languageLength=" + languageLength +
                ", mathLength=" + mathLength +
                ", otherLength=" + otherLength +
                '}';
    }
}
