package com.akash.todoapp.modules.createTodo.view;

import com.akash.todoapp.base.BaseView;
import com.akash.todoapp.model.database.TodoEntity;

import io.reactivex.Observable;


public interface ICreateTodoView extends BaseView {

    Observable<Object> getBackClickObservable();
    Observable<Object> getPriorityClickObservable();
    TodoEntity getTodo();
    void dismiss();
    void showPriorityChooser();
}
