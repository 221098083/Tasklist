package com.se.tasklist.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskInfo implements EntityInfo{
    @PrimaryKey(autoGenerate = false)
    private long id;

    private String name;
    private long taskList;
    private long label;

    private String ddl;

    public TaskInfo(String name,long taskList){
        this.name=name;
        this.taskList=taskList;
        this.label=-1L;
        this.ddl=null;
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

    public long getTaskList() {
        return taskList;
    }

    public void setTaskList(long taskList) {
        this.taskList = taskList;
    }

    public long getLabel() {
        return label;
    }

    public void setLabel(long label) {
        this.label = label;
    }

    public String getDdl() {
        return ddl;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }
}
