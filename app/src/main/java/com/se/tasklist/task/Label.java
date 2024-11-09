package com.se.tasklist.task;

import com.se.tasklist.database.entity.LabelInfo;

public class Label extends AbstractTaskList implements Comparable<Label>{
    private LabelInfo info;

    public Label(LabelInfo info){
        this.info=info;
    }

    @Override
    public LabelInfo getInfo() {
        return info;
    }

    public void setInfo(LabelInfo info) {
        this.info = info;
    }

    @Override
    public int compareTo(Label another){
        return Long.compare(this.info.getId(),another.info.getId());
    }
}
