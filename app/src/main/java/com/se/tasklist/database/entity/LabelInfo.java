package com.se.tasklist.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LabelInfo implements EntityInfo{
    @PrimaryKey(autoGenerate = false)
    private long id;

    private String name;

    private int color;

    public LabelInfo(String name,int color){
        this.name=name;
        this.color=color;
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
}
