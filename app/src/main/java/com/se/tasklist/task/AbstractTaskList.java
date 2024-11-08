package com.se.tasklist.task;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractTaskList implements TaskList{

    protected List<Task> tasks=new LinkedList<>();

    public List<Task> getTasks(){
        return this.tasks;
    }

    public void addTask(Task task) { this.tasks.add(task); }

    public void sortTasks() { this.tasks.sort(Task::compareTo); }

}
