package com.leaning_machine.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.leaning_machine.db.entity.UsedTimeEntity;

import java.util.List;

/**
 *
 * @author John
 * @date 2021/9/16
 */
@Dao
public interface UsedTimeDao {

    @Query("SELECT * FROM USED_TIME")
    List<UsedTimeEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsedTime(UsedTimeEntity usedTimeEntity);

    @Query("SELECT * FROM USED_TIME WHERE USED_DATE =:date")
    UsedTimeEntity getUsedTimeEntity(long date);
}
