package com.leaning_machine.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.leaning_machine.db.dao.UsedPackageDao;
import com.leaning_machine.db.dao.UsedTimeDao;
import com.leaning_machine.db.entity.UsedPackageEntity;
import com.leaning_machine.db.entity.UsedTimeEntity;

/**
 * Created by John on 2021/9/16.
 */
@Database(entities = {UsedTimeEntity.class, UsedPackageEntity.class}, version = 2, exportSchema = false)
public abstract class GlobalDatabase extends RoomDatabase {
    public static String DATABASE_NAME = "learn-db";

    public abstract UsedTimeDao usedTimeDao();
    public abstract UsedPackageDao usedPackageDao();

    private static GlobalDatabase INSTANCE;
    private static final Object sLock = new Object();

    public static GlobalDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), GlobalDatabase.class, DATABASE_NAME).build();
            }
            return INSTANCE;
        }
    }
}
