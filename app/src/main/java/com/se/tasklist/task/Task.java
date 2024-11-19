package com.se.tasklist.task;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.se.tasklist.database.entity.TaskInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Comparable<Task>{

    private TaskInfo info;

    /*Attributes concerning task reminding.*/
    public enum Task_Cycle{
        ONCE,
        WEEKLY,
        MONTHLY
    }

    public enum Remind_Schema{
        EVERYDAY,
        ONE_DAY_BEFORE,
        THREE_DAYS_BEFORE,
        ONE_WEEK_BEFORE
    }

    public Task(TaskInfo info){
        this.info=info;
    }

    public TaskInfo getInfo() {
        return info;
    }

    public void setInfo(TaskInfo info) {
        this.info = info;
    }

    public int compareTo(Task another){
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        Date d1,d2;
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