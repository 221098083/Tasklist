package com.se.tasklist;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.se.tasklist.adapter.LabelAdapter;
import com.se.tasklist.adapter.TaskListAdapter;
import com.se.tasklist.task.UserTaskList;

public class NavigatorFragment extends Fragment {

    private MessageListener listener;

    ImageButton createListButton;
    EditText createListText;

    ImageButton createLabelButton;
    EditText createLabelText;

    ListView defaultListGroup;
    ListView userListGroup;
    ListView labelListGroup;

    TaskListAdapter defaultListAdapter;
    TaskListAdapter userListAdapter;
    LabelAdapter labelAdapter;

    public NavigatorFragment() {
        // Required empty public constructor
    }

    public static NavigatorFragment newInstance() {
        NavigatorFragment fragment = new NavigatorFragment();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_navigator, container, false);

        defaultListGroup=view.findViewById(R.id.default_listgroup);
        defaultListAdapter=new TaskListAdapter(this.getActivity(),listener.getDefaultTaskLists());
        defaultListGroup.setAdapter(defaultListAdapter);
        defaultListGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                long id=defaultListAdapter.getItemId(i);
                Log.d(TAG,"item selected:"+id);
                listener.onTaskListSwitched(id);
            }
        });

        userListGroup=view.findViewById(R.id.user_listgroup);
        userListAdapter=new TaskListAdapter(this.getActivity(),listener.getUserTaskLists());
        userListGroup.setAdapter(userListAdapter);
        userListGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                long id=userListAdapter.getItemId(i);
                Log.d(TAG,"item selected:"+id);
                listener.onTaskListSwitched(id);
            }

        });

        labelListGroup=view.findViewById(R.id.label_listgroup);
        labelAdapter=new LabelAdapter(this.getActivity(),listener.getLabels());
        labelListGroup.setAdapter(labelAdapter);
        labelListGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                long id=labelAdapter.getItemId(i);
                listener.onTaskListSwitched(id);
            }

        });

        /*Listener: Create a new TaskList.*/
        createListButton=view.findViewById(R.id.create_list_button);
        createListText=view.findViewById(R.id.create_list_text);

        createListButton.setOnClickListener(view1 -> {
            String name=createListText.getText().toString();
            if(name==null||name.length()==0){
                return;
            }
            listener.createTaskList(name);
            userListAdapter.notifyDataSetChanged();
            createListText.setText("");
            createListText.clearFocus();
            InputMethodManager imm = (InputMethodManager)this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(createListText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        });

        /*Listener: Create a new label.*/
        createLabelButton=view.findViewById(R.id.create_label_button);
        createLabelText=view.findViewById(R.id.create_label_text);

        createLabelButton.setOnClickListener(view1 -> {
            String name=createLabelText.getText().toString();
            if(name==null||name.length()==0){
                return;
            }
            listener.createLabel(name);
            labelAdapter.notifyDataSetChanged();
            createLabelText.setText("");
            createLabelText.clearFocus();
            InputMethodManager imm = (InputMethodManager)this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(createLabelText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        });

        return view;
    }
}