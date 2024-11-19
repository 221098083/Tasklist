package com.se.tasklist.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.se.tasklist.R;
import com.se.tasklist.task.Label;

import java.util.List;

public class LabelAdapter extends BaseAdapter {


    private final Context context;
    private final List<Label> labels;

    public LabelAdapter(Context context, List<Label> labels) {
        this.context = context;
        this.labels = labels;
    }

    @Override
    public int getCount() {
        return labels.size();
    }

    @Override
    public Object getItem(int position) {
        return labels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return labels.get(position).getInfo().getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.tasklist_item, null);
            holder = new ViewHolder();
            holder.labelIcon=convertView.findViewById(R.id.list_icon);
            holder.labelName = convertView.findViewById(R.id.list_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Label label = labels.get(position);
        @SuppressLint("UseCompatLoadingForDrawables") GradientDrawable icon=(GradientDrawable) context.getDrawable(R.drawable.list_title_label);
        icon.setColor(label.getInfo().getColor());
        holder.labelIcon.setImageDrawable(icon);
        holder.labelName.setText(label.getInfo().getName());
        holder.labelName.setWillNotDraw(false);
        return convertView;
    }

    public final class ViewHolder {
        public ImageView labelIcon;
        public TextView labelName;
    }
}
