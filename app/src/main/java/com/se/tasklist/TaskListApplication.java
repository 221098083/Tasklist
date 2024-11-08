package com.se.tasklist;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import com.se.tasklist.database.UserInfoDatabase;

public class TaskListApplication extends Application {
    private static TaskListApplication app;
    private static UserInfoDatabase database;
    @Override
    public void onCreate(){
        super.onCreate();
        app=this;
        database= Room.databaseBuilder(this, UserInfoDatabase.class,"UserInfo")
                .addMigrations()
                .allowMainThreadQueries()
                .build();
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        Log.d(TAG,"on Terminate.");
    }

    public UserInfoDatabase getUserInfoDatabase(){ return database; }

    public static TaskListApplication getInstance(){
        return app;
    }
}
