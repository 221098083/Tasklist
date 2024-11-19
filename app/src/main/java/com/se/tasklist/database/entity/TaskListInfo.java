package com.se.tasklist.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class TaskListInfo implements EntityInfo{
    @PrimaryKey(autoGenerate = false)
    private long id;

    private String name;

    private String createTime;

    public TaskListInfo(String name){
        this.name=name;
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        this.createTime= fmt.format(Calendar.getInstance().getTime());
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
