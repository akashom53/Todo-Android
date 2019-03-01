package com.akash.todoapp.modules.todoList.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import com.akash.todoapp.R;
import com.akash.todoapp.base.BaseActivity;
import com.akash.todoapp.model.database.TodoEntity;
import com.akash.todoapp.modules.createTodo.view.CreateTodoView;
import com.akash.todoapp.modules.todoList.TodoListPresenter;
import com.akash.todoapp.utils.SizeUtils;
import com.akash.todoapp.utils.SpacesItemDecoration;
import com.jakewharton.rxbinding2.view.RxView;
import java.util.List;
import io.reactivex.Observable;

public class TodoListView extends BaseActivity<TodoListPresenter> implements ITodoListView {

    private Toolbar mToolbar;
    private Button mBtnCreateTodo;
    private RecyclerView mRecyclerViewTodoList;
    private TodoListAdapter adapter;
    private static final int CREATE_TODO_CODE = 878;

    @Override
    protected Class<TodoListPresenter> getPresenterClass() {
        return TodoListPresenter.class;
    }

    @Override
    protected void onViewReady() {
        setupRecyclerView();
    }

    @Override
    protected void findViews() {
        mToolbar = findViewById(R.id.toolbar);
        mBtnCreateTodo = findViewById(R.id.btn_create_todo);
        mRecyclerViewTodoList = findViewById(R.id.recycler_view_todo_list);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_todo_list;
    }


    @Override
    protected void onStop() {
        this.presenter.disposeObservables();
        super.onStop();
    }

    @Override
    public Observable<Object> getCreateButtonObservable() {
        return RxView.clicks(mBtnCreateTodo);
    }

    private void setupRecyclerView() {
        int spacingInPixels = SizeUtils.dpToPx(6);
        mRecyclerViewTodoList.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter = new TodoListAdapter();
        mRecyclerViewTodoList.setAdapter(adapter);
        mRecyclerViewTodoList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }


    @Override
    public void setTodoList(List<TodoEntity> todoList) {
        adapter.setTodoList(todoList);
    }

    @Override
    public void showCreateTodoUI() {
        Intent intent = new Intent(this, CreateTodoView.class);
        startActivityForResult(intent, CREATE_TODO_CODE);
//        overridePendingTransition(R.anim.slide_up, R.anim.stay);
    }

    @Override
    public Observable<TodoEntity> getRemoveButtonObservable() {
        return adapter.getRemoveClickObservable();
    }

}
