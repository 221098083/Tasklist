package com.se.tasklist.database.dao;

import androidx.room.Dao;
import androidx.room.*;

import com.se.tasklist.database.entity.*;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(TaskInfo... task);

    @Delete
    void delete(TaskInfo... task);

    @Update
    int update(TaskInfo... task);

    @Query("SELECT * FROM TaskInfo")
    List<TaskInfo> queryAll();
}
