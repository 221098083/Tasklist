package com.se.tasklist.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskInfo {
    @PrimaryKey(autoGenerate = false)
    private Long id;

    private String name;
    private Long taskList;
    private Long label;

    public TaskInfo(String name,long taskList){
        this.name=name;
        this.taskList=taskList;
        this.label=-1L;
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

    public Long getTaskList() {
        return taskList;
    }

    public void setTaskList(Long taskList) {
        this.taskList = taskList;
    }

    public Long getLabel() {
        return label;
    }

    public void setLabel(Long label) {
        this.label = label;
    }
}
