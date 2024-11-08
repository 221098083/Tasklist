package com.se.tasklist.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.se.tasklist.database.entity.TaskListInfo;

import java.util.List;

@Dao
public interface TaskListDao {
    @Insert
    void insert(TaskListInfo... task);

    @Delete
    void delete(TaskListInfo... task);

    @Update
    int update(TaskListInfo... task);

    @Query("SELECT * FROM TaskListInfo")
    List<TaskListInfo> queryAll();
}
