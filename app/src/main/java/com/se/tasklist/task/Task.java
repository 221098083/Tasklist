package com.se.tasklist.task;

import com.se.tasklist.database.entity.TaskInfo;

public class Task implements Comparable<Task>{

    private TaskInfo info;

    /*Attributes concerning task reminding.*/
    public enum Task_Cycle{
        ONCE,
        WEEKLY,
        MONTHLY;
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
        //TODO: implement the logic of task comparing.
        return Long.compare(this.info.getId(),another.info.getId());
    }
}