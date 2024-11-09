package com.se.tasklist;

import android.app.Activity;
import android.content.Context;

import com.se.tasklist.task.Label;
import com.se.tasklist.task.Task;
import com.se.tasklist.task.TaskList;
import com.se.tasklist.task.UserTaskList;

import java.util.List;

public interface MessageListener {
    public Activity getActivity();
    public void onTaskListSwitched(long target_list);

    public List<UserTaskList> getDefaultTaskLists();
    public List<UserTaskList> getUserTaskLists();
    public List<Label> getLabels();

    public List<Task> getCurrentTaskListContent();

    public void createTaskList(String name);

    public void createTask(String name);

    public String getCurrentTaskListName();

}
