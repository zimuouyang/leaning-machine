package com.leaning_machine.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.leaning_machine.db.entity.UsedPackageEntity;
import com.leaning_machine.db.entity.UsedTimeEntity;

import java.util.List;

/**
 *
 * @author John
 * @date 2021/9/16
 */
@Dao
public interface UsedPackageDao {

    @Query("SELECT * FROM USED_PACKAGE_TIME ORDER BY ID ASC")
    List<UsedPackageEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsedTime(UsedPackageEntity usedPackageEntity);

    @Query("SELECT * FROM used_package_time WHERE USED_DATE =:date")
    UsedPackageEntity getUsedTimeEntity(String date);

}
