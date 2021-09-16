package com.leaning_machine.db.dao;

import androidx.room.Insert;
import androidx.room.Query;

import com.leaning_machine.db.entity.UsedTimeEntity;

import java.util.List;

/**
 *
 * @author John
 * @date 2021/9/16
 */
public interface UsedTimeDao {

    @Query("SELECT * FROM USED_TIME")
    List<UsedTimeEntity> getAll();

    @Insert
    void insertUsedTime(UsedTimeEntity usedTimeEntity);
}
