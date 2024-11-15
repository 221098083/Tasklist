package com.se.tasklist.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class LabelInfo implements EntityInfo{
    @PrimaryKey(autoGenerate = false)
    private long id;

    private String name;

    private int color;

    private String createTime;

    public LabelInfo(String name,int color){
        this.name=name;
        this.color=color;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
