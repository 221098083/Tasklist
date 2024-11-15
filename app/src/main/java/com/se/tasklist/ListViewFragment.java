package com.se.tasklist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.se.tasklist.adapter.TaskAdapter;
import com.se.tasklist.task.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListViewFragment extends Fragment {

    private MessageListener listener;

    View view;

    ImageButton createTaskButton;
    EditText createTaskText;

    ImageView listTitleLabel;
    TextView taskListName;

    ListView taskListContent;

    TaskAdapter taskAdapter;

    public ListViewFragment() {
        // Required empty public constructor
    }

    public static ListViewFragment newInstance() {
        ListViewFragment fragment = new ListViewFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            this.listener=(MessageListener)context;
        }
        catch (Exception e){
            throw new ClassCastException();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_list_view, container, false);
        taskListContent=view.findViewById(R.id.task_list_content);
        taskAdapter=new TaskAdapter(listener.getActivity(),listener.getCurrentTaskListContent());
        taskListContent.setAdapter(taskAdapter);
        taskListContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task=(Task)taskAdapter.getItem(i);
                showDetail(task);
            }
        });

        listTitleLabel=view.findViewById(R.id.list_title_label);
        taskListName=view.findViewById(R.id.task_list_name);
        taskListName.setWillNotDraw(false);

        createTaskButton=view.findViewById(R.id.create_task_button);
        createTaskText=view.findViewById(R.id.create_task_text);

        createTaskButton.setOnClickListener(view1 -> {
            String name=createTaskText.getText().toString();
            if(name.length()==0){
                return;
            }
            listener.createTask(name);
            createTaskText.setText("");
            createTaskText.clearFocus();
            InputMethodManager imm = (InputMethodManager)listener.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(createTaskText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            refresh();
        });

        refresh();

        return view;
    }

    public void refresh(){
        @SuppressLint("UseCompatLoadingForDrawables") GradientDrawable icon=(GradientDrawable) listener.getActivity().getDrawable(R.drawable.list_title_label);
        icon.setColor(listener.getLabelColor());
        listTitleLabel.setImageDrawable(icon);
        taskListName.setText(listener.getCurrentTaskListName());
        taskAdapter.notifyDataSetChanged();
    }

    private void showDetail(Task task){
        View layout=LayoutInflater.from(this.getActivity()).inflate(R.layout.details,null);
        PopupWindow taskDetails=new PopupWindow(layout,ViewGroup.LayoutParams.MATCH_PARENT,1000);
        taskDetails.setFocusable(true);
        taskDetails.setOutsideTouchable(true);

        TextView taskDetailName=layout.findViewById(R.id.task_detail_name);
        taskDetailName.setText(task.getInfo().getName());

        ImageView detailLabelIcon=layout.findViewById(R.id.detail_label_icon);
        @SuppressLint("UseCompatLoadingForDrawables") GradientDrawable icon=(GradientDrawable) this.getActivity().getDrawable(R.drawable.list_title_label);
        if(task.getInfo().getLabel()==-1L){
            icon.setColor(getResources().getColor(R.color.divider, this.getActivity().getTheme()));
        }
        else {
            icon.setColor(listener.getLabelColor(task.getInfo().getLabel()));
        }
        detailLabelIcon.setImageDrawable(icon);
        TextView detailLabelName=layout.findViewById(R.id.detail_label_name);
        detailLabelName.setText(listener.getLabelName(task.getInfo().getLabel()));

        taskDetails.showAtLocation(view.findViewById(R.id.list_view_main), Gravity.BOTTOM|Gravity.RIGHT,0,0);
    }
}

