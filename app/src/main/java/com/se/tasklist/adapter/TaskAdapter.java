package com.se.tasklist.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.se.tasklist.MainActivity;
import com.se.tasklist.R;
import com.se.tasklist.task.Task;

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
            holder.taskDone=convertView.findViewById(R.id.task_done);
            holder.taskDone.setOnCheckedChangeListener((compoundButton, b) -> {
                MainActivity activity=(MainActivity)context;
                ViewHolder viewHolder=(ViewHolder) compoundButton.getTag();
                activity.setTaskDone(viewHolder.taskId,b);
            });
            holder.taskName=convertView.findViewById(R.id.task_name);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder) convertView.getTag();
        }
        Task task=tasks.get(position);
        holder.taskId=task.getInfo().getId();
        holder.taskName.setText(task.getInfo().getName());
        holder.taskName.setWillNotDraw(false);
        holder.taskDone.setTag(holder);
        holder.taskDone.setChecked(task.getInfo().getDone() == 1);

        return convertView;
    }

    public final class ViewHolder{
        public CheckBox taskDone;
        public TextView taskName;
        public long taskId;
    }

}
