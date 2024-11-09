package com.se.tasklist;
import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.se.tasklist.databinding.ActivityMainBinding;
import com.se.tasklist.task.Label;
import com.se.tasklist.task.Task;
import com.se.tasklist.task.TaskManager;
import com.se.tasklist.task.UserTaskList;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MessageListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private TaskManager taskManager;

    FragmentTransaction fragmentTransaction;
    NavigatorFragment navigatorFragment;
    ListViewFragment listViewFragment;

    List<UserTaskList> defaultTaskLists;
    List<UserTaskList> userTaskLists;
    List<Label> labels;

    List<Task> currentTaskListContent;

    private long tasklist_selected=0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"on create");

        this.taskManager=TaskManager.initTaskManager();
        initFragments();
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    private void initFragments(){
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        navigatorFragment=NavigatorFragment.newInstance();
        fragmentTransaction.add(R.id.fcv_navigator,navigatorFragment);
        listViewFragment=ListViewFragment.newInstance();
        fragmentTransaction.add(R.id.fcv_listview,listViewFragment);
        fragmentTransaction.commit();
        hideFragment(navigatorFragment);
        hideFragment(listViewFragment);
        showFragment(navigatorFragment);
        showFragment(listViewFragment);
    }

    private void showFragment(Fragment fragment){
        if(fragment!=null){
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.show(fragment);
            fragmentTransaction.commit();
        }
    }

    private void hideFragment(Fragment fragment){
        if(fragment!=null){
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commit();
        }
    }

    public Activity getActivity(){
        return this;
    }

    @Override
    public void onTaskListSwitched(long target_list){
        tasklist_selected=target_list;
        this.currentTaskListContent.clear();
        this.currentTaskListContent.addAll(taskManager.getTasksFromList(tasklist_selected));
        listViewFragment.refresh();
    }

    @Override
    public List<UserTaskList> getDefaultTaskLists(){
        if(this.defaultTaskLists==null) {
            this.defaultTaskLists = new LinkedList<>(this.taskManager.getDefaultTaskLists());
        }
        return this.defaultTaskLists;
    }

    @Override
    public List<UserTaskList> getUserTaskLists(){
        if(this.userTaskLists==null) {
            this.userTaskLists=new LinkedList<>(taskManager.getUserTaskLists());
        }
        return this.userTaskLists;
    }

    @Override
    public List<Label> getLabels(){
        if(this.labels==null) {
            this.labels=new LinkedList<>(taskManager.getLabels());
        }
        return this.labels;
    }

    @Override
    public void createTaskList(String name) {
        UserTaskList taskList=taskManager.createTaskList(name);
        this.userTaskLists.add(taskList);
    }

    @Override
    public void createLabel(String name){
        Label label=taskManager.createLabel(name);
        this.labels.add(label);
    }

    @Override
    public List<Task> getCurrentTaskListContent(){
        if(this.currentTaskListContent==null){
            this.currentTaskListContent=new LinkedList<>(taskManager.getTasksFromList(tasklist_selected));
        }
        return this.currentTaskListContent;
    }

    @Override
    public void createTask(String name){
        Task task=taskManager.createTask(name,tasklist_selected);
        currentTaskListContent.add(task);
    }

    @Override
    public String getCurrentTaskListName(){
        return taskManager.getTaskListById(tasklist_selected).getInfo().getName();
    }

    @Override
    public int getLabelColor(){
        if(tasklist_selected<500){
            return 0xFFFFCF7F;
        }
        Label label=(Label)(taskManager.getTaskListById(tasklist_selected));
        return label.getInfo().getColor();
    }

}