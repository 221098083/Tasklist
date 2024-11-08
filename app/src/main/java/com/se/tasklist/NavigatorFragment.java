package com.se.tasklist;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        defaultListGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                long id=adapterView.getSelectedItemId();
                listener.onTaskListSwitched(id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        userListGroup=view.findViewById(R.id.user_listgroup);
        userListAdapter=new TaskListAdapter(this.getActivity(),listener.getUserTaskLists());
        userListGroup.setAdapter(userListAdapter);

        labelListGroup=view.findViewById(R.id.label_listgroup);
        labelAdapter=new LabelAdapter(this.getActivity(),listener.getLabels());
        labelListGroup.setAdapter(labelAdapter);

        createListButton=view.findViewById(R.id.create_list_button);
        createListText=view.findViewById(R.id.create_list_text);

        createListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=createListText.getText().toString();
                if(name==null||name.length()==0){
                    return;
                }
                listener.createTaskList(name);
                userListAdapter.notifyDataSetChanged();
                createListText.setText("");
                createListText.clearFocus();
            }
        });



        return view;
    }
}