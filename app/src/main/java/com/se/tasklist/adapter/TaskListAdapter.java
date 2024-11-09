package com.se.tasklist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.se.tasklist.NavigatorFragment;
import com.se.tasklist.R;
import com.se.tasklist.task.TaskList;
import com.se.tasklist.task.UserTaskList;

import java.util.List;

public class TaskListAdapter extends BaseAdapter {

    private Context context;
    private List<UserTaskList> taskLists;

    public TaskListAdapter(Context context,List<UserTaskList> taskLists){
        this.context=context;
        this.taskLists=taskLists;
    }

    @Override
    public int getCount() {
        return taskLists.size();
    }

    @Override
    public Object getItem(int position) {
        return taskLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return taskLists.get(position).getInfo().getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.tasklist_item, null);
            holder=new ViewHolder();
            holder.listName=convertView.findViewById(R.id.list_name);
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder)convertView.getTag();
        }
        UserTaskList taskList=taskLists.get(position);
        holder.listName.setText(taskList.getInfo().getName());
        holder.listName.setWillNotDraw(false);
        return convertView;
    }

    public final class ViewHolder{
        public TextView listName;
    }
}


