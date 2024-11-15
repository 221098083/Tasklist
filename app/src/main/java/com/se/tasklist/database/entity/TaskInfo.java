package com.se.tasklist.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class TaskInfo implements EntityInfo{
    @PrimaryKey(autoGenerate = false)
    private long id;

    private String name;
    private long taskList;
    private long label;

    private String createTime;

    private String ddl;

    private int important;
    private int done;

    public TaskInfo(String name,long taskList){
        this.name=name;
        this.taskList=taskList;
        this.label=-1L;
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        this.createTime= fmt.format(Calendar.getInstance().getTime());
        this.ddl=null;
        this.important=0;
        this.done=0;
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

    @Override
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getImportant() {
        return important;
    }

    public void setImportant(int important) {
        this.important = important;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public String getDdl() {
        return ddl;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }
}
