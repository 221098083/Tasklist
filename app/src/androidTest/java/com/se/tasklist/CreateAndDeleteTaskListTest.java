package com.se.tasklist;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.se.tasklist.database.UserInfoDatabase;
import com.se.tasklist.exceptions.LabelIdOutOfRange;
import com.se.tasklist.exceptions.TaskIdOutOfRange;
import com.se.tasklist.exceptions.TaskListIdOutOfRange;
import com.se.tasklist.task.Label;
import com.se.tasklist.task.Task;
import com.se.tasklist.task.TaskManager;
import com.se.tasklist.task.UserTaskList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Testing the creation and deletion of task lists, tasks and labels.
 */
@RunWith(AndroidJUnit4.class)
public class CreateAndDeleteTaskListTest {
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

    @Test(expected = TaskListIdOutOfRange.class)
    public void createTaskListTest() throws TaskListIdOutOfRange {
        for(long i=3;i<=TaskManager.USER_TASKLIST_HIGH;++i){
            UserTaskList taskList=taskDataManager.createTaskList("List"+i);
            assertEquals(taskList.getInfo().getId(),i);
            assertEquals(taskList,(UserTaskList) taskDataManager.getTaskListById(i));
        }
        fail("taskDataManager didn't throw TaskListIdOutOfRange exception");
    }

    @Test
    public void createAndDeleteTaskListTest() throws TaskListIdOutOfRange {
        UserTaskList taskList = taskDataManager.createTaskList("Test List");
        assertNotNull(taskList);
        assertEquals(taskList,taskDataManager.getTaskListById(taskList.getInfo().getId()));

        taskDataManager.deleteTaskList(taskList.getInfo().getId());
        assertNull(taskDataManager.getTaskListById(taskList.getInfo().getId()));
    }

    @Test
    public void createAndDeleteTaskTest() throws TaskIdOutOfRange {
        Task task = taskDataManager.createTask("Test Task", 0L);
        assertNotNull(task);
        assertEquals("Test Task", task.getInfo().getName());
        assertEquals(0L, task.getInfo().getTaskList());

        taskDataManager.deleteTask(task.getInfo().getId());
        assertNull(taskDataManager.getTaskById(task.getInfo().getId()));
    }

    @Test(expected = LabelIdOutOfRange.class)
    public void createLabelTest() throws LabelIdOutOfRange {
        for(long i=TaskManager.LABEL_LOW;i<=TaskManager.LABEL_HIGH;++i){
           Label label=taskDataManager.createLabel("Label"+i);
            assertEquals(label.getInfo().getId(),i);
            assertEquals(label,(Label) taskDataManager.getTaskListById(i));
        }
        fail("taskDataManager didn't throw LabelIdOutOfRange exception");
    }

    @Test
    public void createAndDeleteLabelTest() throws LabelIdOutOfRange {
        Label label = taskDataManager.createLabel("Test Label");
        assertNotNull(label);
        assertEquals("Test Label", label.getInfo().getName());

        taskDataManager.deleteTaskList(label.getInfo().getId());
        assertNull(taskDataManager.getTaskListById(label.getInfo().getId()));
    }

    @Test
    public void deleteTaskFromListTest() throws TaskListIdOutOfRange, TaskIdOutOfRange {
        Random rand=new Random();
        for(long i=3;i<10;++i){
            taskDataManager.createTaskList("List"+i);
            assertEquals(i,taskDataManager.getTaskListById(i).getInfo().getId());
        }
        for(long i=0;i<100;++i){
            taskDataManager.createTask("Task"+i, rand.nextInt(6)+3);
            assertEquals(i,taskDataManager.getTaskById(i).getInfo().getId());
        }
        assertEquals(100, taskDataManager.getTasksFromList(0L).size());
        for(long i=0;i<100;++i){
            taskDataManager.deleteTask(i);
            assertNull(taskDataManager.getTaskById(i));
        }
        for(long i=3;i<10;++i){
            assertEquals(0, taskDataManager.getTasksFromList(i).size());
        }
        assertEquals(0, taskDataManager.getTasksFromList(0L).size());
    }

    @Test(expected = Exception.class)
    public void createTaskWithIllegalTaskListIdTest() throws Exception{
        taskDataManager.createTask("Test Task", -1L);
        fail("taskDataManager didn't throw an exception");
    }

    @Test
    public void deleteTaskListTest() throws TaskListIdOutOfRange, TaskIdOutOfRange {
        for(long i=3;i<10;++i){
            taskDataManager.createTaskList("List"+i);
            assertEquals(i,taskDataManager.getTaskListById(i).getInfo().getId());
        }
        for(long i=3;i<10;++i){
            for(long j=0;j<10;++j){
                Task task=taskDataManager.createTask("Task"+j, i);
                assertEquals((i-3)*10+j,task.getInfo().getId());
            }
        }
        for(long i=3;i<10;++i){
            taskDataManager.deleteTaskList(i);
            assertNull(taskDataManager.getTaskListById(i));
            for(long j=0;j<10;++j){
                assertNull(taskDataManager.getTaskById((i-3)*10+j));
            }
        }
        assertEquals(0, taskDataManager.getTasksFromList(0L).size());
    }

    @Test(expected = Exception.class)
    public void deleteTaskListWithIllegalTaskListIdTest(){
        taskDataManager.deleteTaskList(0L);
        fail("taskDataManager didn't throw an exception");
    }

    @Test(expected = Exception.class)
    public void deleteNotExistingTaskList(){
        taskDataManager.deleteTaskList(3L);
        fail("taskDataManager didn't throw an exception");
    }


    @After
    public void tearDown(){
        database.clearAllTables();
        taskDataManager=null;
    }
}