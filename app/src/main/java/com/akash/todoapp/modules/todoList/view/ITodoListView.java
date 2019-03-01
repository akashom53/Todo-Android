package com.akash.todoapp.modules.todoList.view;

import com.akash.todoapp.base.BaseView;
import com.akash.todoapp.model.database.TodoEntity;

import java.util.List;

import io.reactivex.Observable;

public interface ITodoListView extends BaseView {
    Observable<Object> getCreateButtonObservable();
    void setTodoList(List<TodoEntity> todoList);
    void showCreateTodoUI();
    Observable<TodoEntity> getRemoveButtonObservable();
}
