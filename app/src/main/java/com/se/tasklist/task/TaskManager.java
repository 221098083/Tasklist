package com.se.tasklist.task;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.se.tasklist.TaskListApplication;
import com.se.tasklist.database.dao.*;
import com.se.tasklist.database.entity.LabelInfo;
import com.se.tasklist.database.entity.TaskInfo;
import com.se.tasklist.database.entity.TaskListInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TaskManager {

    public static final int MAX_TASK_COUNT=65536;
    public static final int MAX_TASKLIST_COUNT=1000;


    private static TaskManager manager;
    private TaskDataManager taskDataManager;

    private TaskManager(){}

    public static TaskManager initTaskManager(){
        if(manager==null){
            manager=new TaskManager();
            manager.taskDataManager=manager.new TaskDataManager();
        }
        manager.taskDataManager.initialize();
        return manager;
    }

    class TaskDataManager{

        private Map<Long,Task> tasks;
        private Map<Long,UserTaskList> taskLists;
        private Map<Long,Label> labels;

        private TaskDao taskDao;
        private TaskListDao taskListDao;
        private LabelDao labelDao;

        TaskDataManager(){
            this.tasks=new HashMap<>();
            this.taskLists=new HashMap<>();
            this.labels=new HashMap<>();
        }

        public void initialize(){
            if(TaskListApplication.getInstance()==null){
                Log.d(TAG,"null instance.");
            }

            this.taskDao = TaskListApplication.getInstance().getUserInfoDatabase().taskDao();
            this.taskListDao = TaskListApplication.getInstance().getUserInfoDatabase().taskListDao();
            this.labelDao = TaskListApplication.getInstance().getUserInfoDatabase().labelDao();

            loadTasks();
            loadTaskLists();
            loadLabels();

            InitializeTaskListsAndLabels();
        }

        private void loadTasks(){
            List<TaskInfo> taskInfos=this.taskDao.queryAll();
            for(TaskInfo info:taskInfos){
                Task task=new Task(info);
                tasks.put(info.getId(),task);
            }
        }

        private void loadTaskLists(){
            List<TaskListInfo> taskListInfos=this.taskListDao.queryAll();
            for(TaskListInfo info:taskListInfos){
                UserTaskList taskList=new UserTaskList(info);
                taskLists.put(info.getId(),taskList);
            }
        }

        private void loadLabels(){
            List<LabelInfo> labelInfos=this.labelDao.queryAll();
            for(LabelInfo info:labelInfos){
                Label label=new Label(info);
                labels.put(info.getId(), label);
            }
        }

        private void InitializeTaskListsAndLabels(){
            for(Task task:tasks.values()){
                this.taskLists.get(task.getInfo().getTaskList()).addTask(task);
                if(task.getInfo().getLabel()!=-1){
                    this.labels.get(task.getInfo().getLabel()).addTask(task);
                }
            }
            for(UserTaskList taskList:taskLists.values()){
                taskList.sortTasks();
            }
            for(Label label:labels.values()){
                label.sortTasks();
            }
        }

        public List<UserTaskList> getUserTaskLists(){
            List<UserTaskList> ret=new LinkedList<>(this.taskLists.values());
            ret.sort((t1,t2)->t1.compareTo(t2));
            return ret;
        }

        public List<Label> getUserLabels(){
            List<Label> ret=new LinkedList<>(this.labels.values());
            ret.sort((t1,t2)->t1.compareTo(t2));
            return ret;
        }

        public List<Task> getTasksFromList(int taskList_id){
            return this.taskLists.get(taskList_id).getTasks();
        }

        public List<Task> getTasksFromLabel(int label_id){
            return this.labels.get(label_id).getTasks();
        }

        public UserTaskList createTaskList(String name){
            TaskListInfo info=new TaskListInfo(name);
            long id;
            for(id=0;id<MAX_TASKLIST_COUNT;++id){
                if(taskLists.get(id)==null){
                    break;
                }
            }
            info.setId(id);
            this.taskListDao.insert(info);
            UserTaskList taskList=new UserTaskList(info);
            this.taskLists.put(id,taskList);
            return taskList;
        }


    }

    public List<UserTaskList> getDefaultTaskLists(){
        //TODO
        return new LinkedList<>();
    }

    public List<UserTaskList> getUserTaskLists(){
        return this.taskDataManager.getUserTaskLists();
    }

    public List<Label> getLabels(){
        return this.taskDataManager.getUserLabels();
    }

    public UserTaskList createTaskList(String name){
        return this.taskDataManager.createTaskList(name);
    }

}
