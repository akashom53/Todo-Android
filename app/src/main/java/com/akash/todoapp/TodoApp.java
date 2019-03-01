package com.akash.todoapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class TodoApp extends Application {

    public static <T extends AppCompatActivity> void launch(Context context, Class<T> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }


}
