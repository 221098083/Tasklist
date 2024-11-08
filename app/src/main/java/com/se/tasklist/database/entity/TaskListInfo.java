package com.se.tasklist.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.se.tasklist.task.TaskList;

@Entity
public class TaskListInfo {
    @PrimaryKey(autoGenerate = false)
    private Long id;

    private String name;

    public TaskListInfo(String name){
        this.name=name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
