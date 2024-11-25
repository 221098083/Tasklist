package com.se.tasklist;

import android.app.Activity;

import com.se.tasklist.task.Label;
import com.se.tasklist.task.Task;
import com.se.tasklist.task.UserTaskList;

import java.util.List;

/* Interface MessageListener:
* Implemented by the MainActivity to process messages from subsidiary fragments.*/
public interface MessageListener {

    /** Get the activity implementing this listener.
    * @return the Activity implementing this listener.*/
    Activity getActivity();

    /** Called when the navigator fragment requests to switch to another task list.
    * @param target_list: the target task list to switch to
    * */
    void onTaskListSwitched(long target_list);

    /** Get the default task lists. i.e. Home, Important and Group.
    * @return List consisting of the default task lists.*/
    List<UserTaskList> getDefaultTaskLists();

    /** Get the User-defined task lists.
     * @return task lists created by user.*/
    List<UserTaskList> getUserTaskLists();

    /** Get the User-defined labels.
     * @return List of all labels created by user.*/
    List<Label> getLabels();

    /** Get a list of tasks held by current selected task list.
    * @return a list of tasks held by current task list.*/
    List<Task> getCurrentTaskListContent();

    /** Called when the navigator fragment requests to set up a new task list.
     * @param name : the name of the new task list.*/
    void createTaskList(String name);

    /** Called when the navigator fragment requests to set up a new label.
     * @param name : the name of the new label.*/
    void createLabel(String name);

    /** Called when the listview fragment requests to create a new task under current task list.
     * @param name : the name of the new task.*/
    void createTask(String name);

    /** Get the name of the current selected task list.
    * @return the name of current task list.*/
    String getCurrentTaskListName();

    /** Get the name of a label given the id of the label.
    * @param label_id : the id of the label.
    * @return the name of the label with given id.*/
    String getLabelName(long label_id);

    /** Get the argb color of current label if current task list selected is a label.
    * if not, return the default color.
    * @return the argb color of current label or default color.*/
    int getLabelColor();

    /** Get the argb color of a label with given id.
     * @return the argb color of label with given id.*/
    int getLabelColor(long label_id);

    /** Called by the list view fragment when it requests to change the done status of a task.
    * @param task_id : the id of the task.
    * @param done : the new done status of the task.*/
    void setTaskDone(long task_id,boolean done);

    /** Called by the list view fragment when it requests to change the ddl of a task.
     * @param task_id : the id of the task.
     * @param year : year of the new ddl.
     * @param month : month of the new ddl.
     * @param dayOfMonth : day of the new ddl.*/
    void setTaskDdl(long task_id,int year,int month,int dayOfMonth);

    /** Called by the list view fragment when it requests to set a task as important.
     * @param task_id : the id of the task.
     * @param is_important : true if the task should be set important.*/
    void setTaskImportant(long task_id,boolean is_important);

    /** Called by the list view fragment when it requests to delete a task from current task list.
     * @param task_id : the id of the task.*/
    void deleteTask(long task_id);

    /** Called by the navigator fragment when it requests to delete a user-created task list or a label.
     * @param taskList_id : the id of the task list.*/
    void deleteTaskList(long taskList_id);

    /** @return true if the current selected task list is a label.*/
    boolean isCurrentLabel();

}
