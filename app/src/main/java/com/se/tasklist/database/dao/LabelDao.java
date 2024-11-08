package com.se.tasklist.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.se.tasklist.database.entity.LabelInfo;

import java.util.List;

@Dao
public interface LabelDao {
    @Insert
    void insert(LabelInfo... task);

    @Delete
    void delete(LabelInfo... task);

    @Update
    int update(LabelInfo... task);

    @Query("SELECT * FROM LabelInfo")
    List<LabelInfo> queryAll();
}
