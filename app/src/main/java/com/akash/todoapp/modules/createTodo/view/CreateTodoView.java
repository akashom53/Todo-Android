package com.akash.todoapp.modules.createTodo.view;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.akash.todoapp.R;
import com.akash.todoapp.base.BaseActivity;
import com.akash.todoapp.model.database.TodoEntity;
import com.akash.todoapp.modules.createTodo.CreateTodoPresenter;
import com.akash.todoapp.modules.todoList.view.PriorityListAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

public class CreateTodoView extends BaseActivity<CreateTodoPresenter> implements ICreateTodoView, View.OnClickListener {

    private Toolbar mToolbar;
    private Button mBtnPriority;
    private EditText mEtTitle;
    private EditText mEtDesc;
    private ObservableEmitter<Object> backClickEmitter;
    private Object dummy = new Object();
    private static final String[] priorities = {
            "High",
            "Medium",
            "Low"
    };

    @Override
    protected Class<CreateTodoPresenter> getPresenterClass() {
        return CreateTodoPresenter.class;
    }

    @Override
    protected void onViewReady() {
        setupClickListeners();
    }

    @Override
    protected void findViews() {
        mToolbar = findViewById(R.id.toolbar);
        mBtnPriority = findViewById(R.id.btn_priority);
        mEtTitle = findViewById(R.id.et_title);
        mEtDesc = findViewById(R.id.et_desc);
    }

    @Override
    public void onBackPressed() {
        backClickEmitter.onNext(dummy);
    }

    private void setupClickListeners() {
        mToolbar.setNavigationOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_create_todo;
    }


    @Override
    public Observable<Object> getBackClickObservable() {
        return Observable.create(emitter -> this.backClickEmitter = emitter);
    }

    @Override
    public Observable<Object> getPriorityClickObservable() {
        return RxView.clicks(mBtnPriority);
    }

    @Override
    public TodoEntity getTodo() {
        TodoEntity todo = new TodoEntity();
        todo.title = mEtTitle.getText().toString();
        todo.description = mEtDesc.getText().toString();
        switch (mBtnPriority.getText().toString().toUpperCase()) {
            case "LOW":
                todo.priority = TodoEntity.Priority.LOW;
                break;
            case "MEDIUM":
                todo.priority = TodoEntity.Priority.MEDIUM;
                break;
            case "HIGH":
                todo.priority = TodoEntity.Priority.HIGH;
                break;
        }
        return todo;
    }

    @Override
    public void dismiss() {
        finish();
    }

    @Override
    public void showPriorityChooser() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Priority")
                .setAdapter(new PriorityListAdapter(this, priorities), (dialog, which) -> {
                    setPriorityButton(which);
                })
                .create();
        alertDialog.show();
    }

    private void setPriorityButton(int priority) {
        mBtnPriority.setText(priorities[priority]);
        switch (priority) {
            case 0:
                mBtnPriority.setBackgroundTintList(getResources().getColorStateList(R.color.colorHighPriority));
                break;
            case 1:
                mBtnPriority.setBackgroundTintList(getResources().getColorStateList(R.color.colorMediumPriority));
                break;
            case 2:
                mBtnPriority.setBackgroundTintList(getResources().getColorStateList(R.color.colorLowPriority));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        backClickEmitter.onNext(dummy);
    }
}
