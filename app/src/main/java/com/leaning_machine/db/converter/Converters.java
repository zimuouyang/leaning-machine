package com.leaning_machine.db.converter;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created by John on 2021/9/16.
 */
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
