package com.se.tasklist.utils;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
    public static void toast(Context context,String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.show();
    }

}
