package com.se.tasklist.task;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.se.tasklist.database.entity.LabelInfo;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        Date d1= null,d2=null;
        try {
            d1 = fmt.parse(this.getInfo().getCreateTime());
            d2 = fmt.parse(another.getInfo().getCreateTime());
        } catch (ParseException e) {
            Log.d(TAG,"pullshot: fail"+this.getInfo().getCreateTime()+another.getInfo().getCreateTime());
            throw new RuntimeException(e);
        }
        return d1.compareTo(d2);
    }
}
