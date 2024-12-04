package com.se.tasklist;

import android.content.Context;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.se.tasklist.database.UserInfoDatabase;
import com.se.tasklist.exceptions.LabelIdOutOfRange;
import com.se.tasklist.exceptions.TaskIdOutOfRange;
import com.se.tasklist.exceptions.TaskListIdOutOfRange;
import com.se.tasklist.task.Label;
import com.se.tasklist.task.Task;
import com.se.tasklist.task.TaskManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Testing Database related operations of TaskDataManager.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    UserInfoDatabase database;
    private TaskManager.TaskDataManager taskDataManager;
    @Before
    public void setUp(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        database= Room.databaseBuilder(appContext, UserInfoDatabase.class,"UserInfo")
                .addMigrations()
                .allowMainThreadQueries()
                .build();
        taskDataManager=new TaskManager.TaskDataManager();
        taskDataManager.initialize(database.taskDao(),database.taskListDao(),database.labelDao());
    }

    @Test
    public void basicLoadTaskDataTest(){
        assertNotNull(taskDataManager.getTaskListById(0L));
        assertNotNull(taskDataManager.getTaskListById(1L));
        assertNotNull(taskDataManager.getTaskListById(2L));
        assertEquals(3,database.taskListDao().queryAll().size());
    }

    @Test
    public void storeTaskListTest() throws TaskListIdOutOfRange {
        for(long i=3;i<10;++i){
            taskDataManager.createTaskList("List"+i);
        }
        taskDataManager=null;
        taskDataManager=new TaskManager.TaskDataManager();
        taskDataManager.initialize(database.taskDao(),database.taskListDao(),database.labelDao());
        for(long i=3;i<10;++i){
            assertEquals(7,taskDataManager.getUserTaskLists().size());
            assertNotNull(taskDataManager.getTaskListById(i));
            assertEquals("List"+i,taskDataManager.getTaskListById(i).getInfo().getName());
        }
    }

    @Test
    public void storeTaskTest() throws TaskIdOutOfRange {
        for(long i=0;i<100;++i){
            taskDataManager.createTask("Task"+i,0L);
        }
        taskDataManager=null;
        taskDataManager=new TaskManager.TaskDataManager();
        taskDataManager.initialize(database.taskDao(),database.taskListDao(),database.labelDao());
        for(long i=0;i<100;++i){
            assertNotNull(taskDataManager.getTaskById(i));
            assertEquals("Task"+i,taskDataManager.getTaskById(i).getInfo().getName());
        }
    }

    @Test
    public void storeLabelTest() throws LabelIdOutOfRange {
        List<Integer> colors=new LinkedList<>();
        for(long i=0;i<10;++i){
            Label label=taskDataManager.createLabel("Label"+i);
            colors.add(label.getInfo().getColor());
        }
        taskDataManager=null;
        taskDataManager=new TaskManager.TaskDataManager();
        taskDataManager.initialize(database.taskDao(),database.taskListDao(),database.labelDao());
        for(long i=0;i<10;++i){
            assertEquals(10,taskDataManager.getUserLabels().size());
            assertEquals("Label"+i,taskDataManager.getTaskListById(TaskManager.LABEL_LOW+i).getInfo().getName());
            assertNotNull(taskDataManager.getTaskListById(TaskManager.LABEL_LOW+i));
            Label label=(Label) taskDataManager.getTaskListById(TaskManager.LABEL_LOW+i);
            assertEquals((int)colors.get((int)i),label.getInfo().getColor());
        }
    }

    @Test
    public void storeTaskAndTaskListTest() throws TaskListIdOutOfRange, TaskIdOutOfRange {
        for(long i=3;i<10;++i){
            taskDataManager.createTaskList("List"+i);
            for(long j=0;j<10;++j){
                taskDataManager.createTask("Task"+j,i);
            }
            assertEquals(10,taskDataManager.getTasksFromList(i).size());
        }
        assertEquals(7,taskDataManager.getUserTaskLists().size());
        assertEquals(7*10,taskDataManager.getTasksFromList(0L).size());
        taskDataManager=null;
        taskDataManager=new TaskManager.TaskDataManager();
        taskDataManager.initialize(database.taskDao(),database.taskListDao(),database.labelDao());
        for(long i=3;i<10;++i){
            assertNotNull(taskDataManager.getTaskListById(i));
            assertEquals(10,taskDataManager.getTasksFromList(i).size());
        }
        assertEquals(7*10,taskDataManager.getTasksFromList(0L).size());
    }

    @Test
    public void setTaskDDLTest() throws TaskIdOutOfRange {
        for(long i=0;i<100;++i){
            Task task=taskDataManager.createTask("Task"+i,0L);
            taskDataManager.setTaskDdl(task.getInfo().getId(),1919,8,10);
            assertEquals("1919-8-10", task.getInfo().getDdl());
        }
        taskDataManager=null;
        taskDataManager=new TaskManager.TaskDataManager();
        taskDataManager.initialize(database.taskDao(),database.taskListDao(),database.labelDao());
        for(long i=0;i<100;++i) {
            Task task = taskDataManager.getTaskById(i);
            assertEquals("1919-8-10", task.getInfo().getDdl());
        }
    }

    @Test
    public void setTaskImportantTest() throws TaskIdOutOfRange {
        for(long i=0;i<100;++i){
            Task task=taskDataManager.createTask("Task"+i,0L);
            taskDataManager.setTaskImportant(task.getInfo().getId(),true);
            assertEquals(1, task.getInfo().getImportant());
        }
        taskDataManager=null;
        taskDataManager=new TaskManager.TaskDataManager();
        taskDataManager.initialize(database.taskDao(),database.taskListDao(),database.labelDao());
        for(long i=0;i<100;++i) {
            Task task = taskDataManager.getTaskById(i);
            assertEquals(1, task.getInfo().getImportant());
        }
        assertEquals(100,taskDataManager.getTasksFromList(1L).size());
    }

    @Test
    public void deleteTaskTest() throws TaskIdOutOfRange {
        for(long i=0;i<100;++i){
            taskDataManager.createTask("Task"+i,0L);
        }
        taskDataManager=null;
        taskDataManager=new TaskManager.TaskDataManager();
        taskDataManager.initialize(database.taskDao(),database.taskListDao(),database.labelDao());
        for(long i=0;i<100;++i){
            assertNotNull(taskDataManager.getTaskById(i));
            taskDataManager.deleteTask(i);
            assertNull(taskDataManager.getTaskById(i));
        }
        taskDataManager=null;
        taskDataManager=new TaskManager.TaskDataManager();
        taskDataManager.initialize(database.taskDao(),database.taskListDao(),database.labelDao());
        assertEquals(0,database.taskDao().queryAll().size());
    }

    @Test
    public void deleteTaskListTest() throws TaskListIdOutOfRange, TaskIdOutOfRange {
        for(long i=3;i<10;++i){
            taskDataManager.createTaskList("List"+i);
            for(long j=0;j<10;++j){
                taskDataManager.createTask("Task"+j,i);
            }
        }
        taskDataManager=null;
        taskDataManager=new TaskManager.TaskDataManager();
        taskDataManager.initialize(database.taskDao(),database.taskListDao(),database.labelDao());
        for(long i=3;i<10;++i){
            assertNotNull(taskDataManager.getTaskListById(i));
            assertEquals(10,taskDataManager.getTasksFromList(i).size());
            taskDataManager.deleteTaskList(i);
        }
        taskDataManager=null;
        taskDataManager=new TaskManager.TaskDataManager();
        taskDataManager.initialize(database.taskDao(),database.taskListDao(),database.labelDao());
        for(long i=3;i<10;++i){
            assertNull(taskDataManager.getTaskListById(i));
        }
        assertEquals(3,database.taskListDao().queryAll().size());
        assertEquals(0,database.taskDao().queryAll().size());
    }

    @Test
    public void deleteLabelTest() throws LabelIdOutOfRange {
        for(long i=0;i<10;++i){
            taskDataManager.createLabel("Label"+i);
        }
        taskDataManager=null;
        taskDataManager=new TaskManager.TaskDataManager();
        taskDataManager.initialize(database.taskDao(),database.taskListDao(),database.labelDao());
        for(long i=0;i<10;++i){
            assertNotNull(taskDataManager.getTaskListById(TaskManager.LABEL_LOW+i));
            taskDataManager.deleteTaskList(TaskManager.LABEL_LOW+i);
        }
        taskDataManager=null;
        taskDataManager=new TaskManager.TaskDataManager();
        taskDataManager.initialize(database.taskDao(),database.taskListDao(),database.labelDao());
        for(long i=0;i<10;++i){
            assertNull(taskDataManager.getTaskListById(TaskManager.LABEL_LOW+i));
        }
        assertEquals(0,database.labelDao().queryAll().size());
    }

    @After
    public void tearDown(){
        database.clearAllTables();
        taskDataManager=null;
    }
}
