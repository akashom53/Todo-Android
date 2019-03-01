package com.akash.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.akash.todoapp.modules.todoList.view.TodoListView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TodoApp.launch(this, TodoListView.class);
    }
}
