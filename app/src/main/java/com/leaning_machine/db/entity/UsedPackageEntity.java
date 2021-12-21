package com.leaning_machine.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "USED_PACKAGE_TIME")
public class UsedPackageEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long id;


    @NonNull
    @ColumnInfo(name = "USED_DATE")
    private String date;

    @NonNull
    @ColumnInfo(name = "PACKAGE_NAME")
    private String packageName;

    @NonNull
    @ColumnInfo(name = "TIME")
    private long time;

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

    @NonNull
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(@NonNull String packageName) {
        this.packageName = packageName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "UsedPackageEntity{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", packageName='" + packageName + '\'' +
                ", time=" + time +
                '}';
    }
}
