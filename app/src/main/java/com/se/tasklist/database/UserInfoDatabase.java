package com.se.tasklist.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.se.tasklist.database.dao.LabelDao;
import com.se.tasklist.database.dao.TaskDao;
import com.se.tasklist.database.dao.TaskListDao;
import com.se.tasklist.database.entity.*;

@Database(entities = {TaskInfo.class, TaskListInfo.class,LabelInfo.class},version=2,exportSchema = false)
public abstract class UserInfoDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract TaskListDao taskListDao();
    public abstract LabelDao labelDao();
}
