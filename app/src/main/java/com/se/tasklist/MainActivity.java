package com.se.tasklist;
import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.se.tasklist.databinding.ActivityMainBinding;
import com.se.tasklist.task.Label;
import com.se.tasklist.task.TaskList;
import com.se.tasklist.task.TaskManager;
import com.se.tasklist.task.UserTaskList;

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

    private long tasklist_selected=0;

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
        showFragment(navigatorFragment);
        showFragment(listViewFragment);
    }

    private void showFragment(Fragment fragment){
        if(fragment!=null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.show(fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onTaskListSwitched(long target_list){
        this.tasklist_selected=target_list;
    }

    @Override
    public List<UserTaskList> getDefaultTaskLists(){
        if(this.defaultTaskLists==null) {
            this.defaultTaskLists = this.taskManager.getDefaultTaskLists();
        }
        return this.defaultTaskLists;
    }

    @Override
    public List<UserTaskList> getUserTaskLists(){
        if(this.userTaskLists==null) {
            this.userTaskLists=taskManager.getUserTaskLists();
        }
        return this.userTaskLists;
    }

    @Override
    public List<Label> getLabels(){
        if(this.labels==null) {
            this.labels=taskManager.getLabels();
        }
        return this.labels;
    }

    @Override
    public void createTaskList(String name) {
        UserTaskList taskList=taskManager.createTaskList(name);
        this.userTaskLists.add(taskList);
    }

}