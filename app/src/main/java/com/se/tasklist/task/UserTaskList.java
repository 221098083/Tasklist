package com.se.tasklist.task;

import com.se.tasklist.database.entity.TaskListInfo;

public class UserTaskList extends AbstractTaskList implements Comparable<UserTaskList>{
    private TaskListInfo info;

    public UserTaskList(TaskListInfo info){
        this.info=info;
    }

    public TaskListInfo getInfo() {
        return info;
    }

    public void setInfo(TaskListInfo info) {
        this.info = info;
    }

    @Override
    public int compareTo(UserTaskList another){
        return Long.compare(this.info.getId(),another.info.getId());
    }
}
