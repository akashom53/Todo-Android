package com.akash.todoapp.modules.todoList;

import android.arch.lifecycle.LiveData;

import com.akash.todoapp.base.BasePresenter;
import com.akash.todoapp.model.TodoRepository;
import com.akash.todoapp.model.database.TodoEntity;
import com.akash.todoapp.modules.todoList.view.ITodoListView;
import com.akash.todoapp.modules.todoList.view.TodoListView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TodoListPresenter extends BasePresenter<ITodoListView> {
    private CompositeDisposable disposables = new CompositeDisposable();
    private LiveData<List<TodoEntity>> todoList;
    private TodoRepository repository;
    private android.arch.lifecycle.Observer<List<TodoEntity>> todoListObserver;

    public TodoListPresenter(TodoListView view) {
        super(view);
        repository = new TodoRepository(view.getApplication());
        todoList = repository.getAllTodos();
    }

    @Override
    public void ready() {
        setupObservables();
        setupListObserver();
    }

    private void setupObservables() {
        getView().getCreateButtonObservable()
                .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(Object object) {
                        createTodo();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        getView().getRemoveButtonObservable()
                .subscribe(new Observer<TodoEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(TodoEntity todoEntity) {
                        repository.deleteTodo(todoEntity);
                        todoList = repository.getAllTodos();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void setupListObserver() {
        todoListObserver = todoList -> getView().setTodoList(todoList);
        todoList.observeForever(todoListObserver);
    }

    private void createTodo() {
        getView().showCreateTodoUI();
    }


    public void disposeObservables() {
//        todoList.removeObserver(todoListObserver);
//        disposables.dispose();
    }
}
