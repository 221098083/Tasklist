package com.se.tasklist;

import com.se.tasklist.task.Label;
import com.se.tasklist.task.TaskList;
import com.se.tasklist.task.UserTaskList;

import java.util.List;

public interface MessageListener {
    public void onTaskListSwitched(long target_list);

    public List<UserTaskList> getDefaultTaskLists();
    public List<UserTaskList> getUserTaskLists();
    public List<Label> getLabels();

    public void createTaskList(String name);

}
