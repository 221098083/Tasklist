package com.se.tasklist;
import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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

    /*FOLLOWING CODE IS FROM: https://blog.csdn.net/qq_60387902/article/details/129692144 */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /* */

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
    public String getLabelName(long label_id){
        if(tasklist_selected<500){
            return "Set a label";
        }
        Label label=(Label)(taskManager.getTaskListById(label_id));
        return label.getInfo().getName();
    }

    @Override
    public int getLabelColor(){
        if(tasklist_selected<500){
            return 0xFFFFCF7F;
        }
        Label label=(Label)(taskManager.getTaskListById(tasklist_selected));
        return label.getInfo().getColor();
    }

    public int getLabelColor(long label_id){
        Label label=(Label)(taskManager.getTaskListById(label_id));
        return label.getInfo().getColor();
    }

    public void setTaskDone(long task_id,boolean done){
        this.taskManager.setTaskDone(task_id,done);
    }

}