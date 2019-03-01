package com.akash.todoapp.modules.todoList.view;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.akash.todoapp.R;

public class PriorityListAdapter extends ArrayAdapter<String> {


    public PriorityListAdapter(Context context, String[] priorities) {
        super(context, R.layout.item_priority, R.id.tv_priority, priorities);
    }
}
