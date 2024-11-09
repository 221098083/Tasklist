package com.se.tasklist.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.se.tasklist.task.TaskList;

@Entity
public class TaskListInfo implements EntityInfo{
    @PrimaryKey(autoGenerate = false)
    private long id;

    private String name;

    public TaskListInfo(String name){
        this.name=name;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
