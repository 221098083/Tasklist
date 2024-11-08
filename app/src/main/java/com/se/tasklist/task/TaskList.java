package com.se.tasklist.task;

import java.util.Iterator;
import java.util.List;

public interface TaskList extends Iterable<Task>{
    public List<Task> getTasks();
    default Iterator<Task> iterator(){return getTasks().iterator();}
}
