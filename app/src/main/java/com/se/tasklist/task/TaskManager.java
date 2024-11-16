package com.se.tasklist.task;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.se.tasklist.TaskListApplication;
import com.se.tasklist.database.dao.*;
import com.se.tasklist.database.entity.LabelInfo;
import com.se.tasklist.database.entity.TaskInfo;
import com.se.tasklist.database.entity.TaskListInfo;
import com.se.tasklist.utils.RandomColorGenerator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TaskManager {

    /*Tasks are numbered 0-65535. */
    public static final int MAX_TASK_COUNT=65536;
    /*Task Lists are numbered 0-999.
    * 0 reserved for Home (present all tasks).
    * 1 is reserved for Important.
    * 2 is reserved for shared Task List(not implemented yet).
    * 3-499 for User Customized Task Lists.
    * 500-999 for User Labels.*/
    //public static final int MAX_TASKLIST_COUNT=1000;
    public static final long USER_TASKLIST_LOW=3L;
    public static final long USER_TASKLIST_HIGH=500L;
    public static final long LABEL_LOW=500L;
    public static final long LABEL_HIGH=1000L;


    private static TaskManager manager;
    private TaskDataManager taskDataManager;

    private TaskManager(){}

    /*Initialize and get the TaskManager instance.*/
    public static TaskManager initTaskManager(){
        if(manager==null){
            manager=new TaskManager();
            manager.taskDataManager=manager.new TaskDataManager();
        }
        manager.taskDataManager.initialize();
        return manager;
    }

    /* TaskDataManager:
    * this inner class is intended for embedded data operation in its outer class TaskManager.
    * and the outer class TaskManager doesn't directly interact with the database but through the TaskDataManager object.*/
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
            if(this.taskLists.size()==0){
                /*First start of the application. Initialize information of the reserved lists.*/
                createTaskList("Home");
                createTaskList("Important");
                createTaskList("Group");
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
                if(task.getInfo().getTaskList()!=0L) {
                    this.taskLists.get(0L).addTask(task);
                }
                this.taskLists.get(task.getInfo().getTaskList()).addTask(task);
                if(task.getInfo().getLabel()!=-1L){
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

        public List<UserTaskList> getDefaultTaskLists(){
            List<UserTaskList> ret=new LinkedList<>();
            ret.add(taskLists.get(0L));
            ret.add(taskLists.get(1L));
            ret.add(taskLists.get(2L));
            return ret;
        }


        public List<UserTaskList> getUserTaskLists(){
            List<UserTaskList> ret=new LinkedList<>(this.taskLists.values());
            ret.remove(taskLists.get(0L));
            ret.remove(taskLists.get(1L));
            ret.remove(taskLists.get(2L));
            ret.sort((t1,t2)->t1.compareTo(t2));
            return ret;
        }

        public List<Label> getUserLabels(){
            List<Label> ret=new LinkedList<>(this.labels.values());
            ret.sort((t1,t2)->t1.compareTo(t2));
            return ret;
        }

        public List<Task> getTasksFromList(long taskList_id){
            Log.d(TAG,"getting tasklist from List "+taskList_id);
            if(taskList_id<USER_TASKLIST_HIGH){
                return this.taskLists.get(taskList_id).getTasks();
            }
            else{
                return this.labels.get(taskList_id).getTasks();
            }
        }

        public List<Task> getTasksFromLabel(long label_id){
            return this.labels.get(label_id).getTasks();
        }

        public UserTaskList createTaskList(String name){
            TaskListInfo info=new TaskListInfo(name);
            long id;
            for(id=0L;id<USER_TASKLIST_HIGH;++id){
                if(taskLists.get(id)==null){
                    break;
                }
            }
            if(id==USER_TASKLIST_HIGH){
                //TODO: THROW EXCEPTION
                return null;
            }
            info.setId(id);
            this.taskListDao.insert(info);
            UserTaskList taskList=new UserTaskList(info);
            this.taskLists.put(id,taskList);
            return taskList;
        }

        public Label createLabel(String name){
            int color=new RandomColorGenerator().generateRandomColor();
            LabelInfo info=new LabelInfo(name,color);
            long id;
            for(id=LABEL_LOW;id<LABEL_HIGH;++id){
                if(labels.get(id)==null){
                    break;
                }
            }
            if(id==LABEL_HIGH){
                //TODO: THROW EXCEPTION
                return null;
            }
            info.setId(id);
            this.labelDao.insert(info);
            Label label=new Label(info);
            this.labels.put(id,label);
            return label;
        }

        public Task createTask(String name,long taskList_id){
            TaskInfo info=new TaskInfo(name,taskList_id);
            Log.d(TAG,"adding task to"+taskList_id);
            long id;
            for(id=0;id<MAX_TASK_COUNT;++id){
                if(tasks.get(id)==null){
                    break;
                }
            }
            if(id==MAX_TASK_COUNT){
                //TODO:THROW EXCEPTION
                return null;
            }
            info.setId(id);
            this.taskDao.insert(info);
            Task task=new Task(info);
            this.tasks.put(id,task);
            if(taskList_id!=0L) {
                UserTaskList home = taskLists.get(0L);
                home.addTask(task);
            }
            UserTaskList taskList=taskLists.get(taskList_id);
            taskList.addTask(task);
            return task;
        }

        public void deleteTask(long task_id){
            Task task=this.tasks.get(task_id);
            this.tasks.remove(task);

            UserTaskList taskList=this.taskLists.get(task.getInfo().getTaskList());
            taskList.removeTask(task);

            if(task.getInfo().getTaskList()!=0L){
                UserTaskList home=this.taskLists.get(0L);
                home.removeTask(task);
            }
            if(task.getInfo().getLabel()!=-1L){
                Label label=this.labels.get(task.getInfo().getLabel());
                label.removeTask(task);
            }
            if(task.getInfo().getImportant()==1){
                UserTaskList important=this.taskLists.get(1L);
                important.removeTask(task);
            }

            TaskInfo info=task.getInfo();
            taskDao.delete(info);
        }

        public Task getTaskById(long task_id){
            return this.tasks.get(task_id);
        }

        public TaskList getTaskListById(long taskList_id){
            if(taskList_id<USER_TASKLIST_HIGH){
                return this.taskLists.get(taskList_id);
            }
            else{
                return this.labels.get(taskList_id);
            }
        }

        public void setTaskDone(long task_id,boolean done){
            Task task=this.tasks.get(task_id);
            TaskInfo info=task.getInfo();
            info.setDone((done?1:0));
            taskDao.update(info);
        }

        public void setTaskDdl(long task_id,int year,int month,int dayOfMonth){
            Task task=this.tasks.get(task_id);
            TaskInfo info=task.getInfo();
            String fmt_ddl=""+year+"-"+month+"-"+dayOfMonth;
            info.setDdl(fmt_ddl);
            taskDao.update(info);
        }

        public void setTaskImportant(long task_id,boolean is_important){
            Task task=this.tasks.get(task_id);
            TaskInfo info=task.getInfo();
            info.setImportant((is_important?1:0));
            taskDao.update(info);
            UserTaskList important=this.taskLists.get(1L);
            if(is_important){
                important.addTask(task);
            }
            else{
                important.removeTask(task);
            }

        }

    }

    public List<UserTaskList> getDefaultTaskLists(){
        return this.taskDataManager.getDefaultTaskLists();
    }

    public List<UserTaskList> getUserTaskLists(){
        return this.taskDataManager.getUserTaskLists();
    }

    public List<Label> getLabels(){
        return this.taskDataManager.getUserLabels();
    }

    public List<Task> getTasksFromList(long taskList_id){
        return taskDataManager.getTasksFromList(taskList_id);
    }

    public UserTaskList createTaskList(String name){
        return this.taskDataManager.createTaskList(name);
    }

    public Label createLabel(String name){
        return this.taskDataManager.createLabel(name);
    }

    public Task createTask(String name,long taskList_id){
        return this.taskDataManager.createTask(name,taskList_id);
    }

    public TaskList getTaskListById(long taskList_id){
        return taskDataManager.getTaskListById(taskList_id);
    }

    public Task getTaskById(long task_id){
        return taskDataManager.getTaskById(task_id);
    }

    public void setTaskDone(long task_id,boolean done){
        this.taskDataManager.setTaskDone(task_id,done);
    }

    public void setTaskDdl(long task_id,int year,int month,int dayOfMonth){
        this.taskDataManager.setTaskDdl(task_id,year,month,dayOfMonth);
    }

    public void setTaskImportant(long task_id,boolean is_important){
        this.taskDataManager.setTaskImportant(task_id,is_important);
    }

    public void deleteTask(long task_id){
        this.taskDataManager.deleteTask(task_id);
    }

}
