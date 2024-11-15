package com.se.tasklist.task;

import com.se.tasklist.database.entity.EntityInfo;

import java.util.Iterator;
import java.util.List;

public interface TaskList extends Iterable<Task>{
    List<Task> getTasks();
    EntityInfo getInfo();
    default Iterator<Task> iterator(){return getTasks().iterator();}
}
