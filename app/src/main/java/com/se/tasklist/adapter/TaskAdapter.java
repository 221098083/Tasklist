package com.se.tasklist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.se.tasklist.R;
import com.se.tasklist.task.Task;
import com.se.tasklist.task.UserTaskList;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private List<Task> tasks;

    public TaskAdapter(Context context,List<Task> tasks){
        this.context=context;
        this.tasks=tasks;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tasks.get(position).getInfo().getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.task_item,null);
            holder=new ViewHolder();
            holder.taskName=convertView.findViewById(R.id.task_name);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder) convertView.getTag();
        }
        Task task=tasks.get(position);
        holder.taskName.setText(task.getInfo().getName());
        holder.taskName.setWillNotDraw(false);
        return convertView;
    }

    public final class ViewHolder{
        public TextView taskName;
    }

}
