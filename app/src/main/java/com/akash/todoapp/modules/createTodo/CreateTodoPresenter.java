package com.akash.todoapp.modules.createTodo;

import com.akash.todoapp.base.BasePresenter;
import com.akash.todoapp.model.TodoRepository;
import com.akash.todoapp.model.database.TodoEntity;
import com.akash.todoapp.modules.createTodo.view.CreateTodoView;
import com.akash.todoapp.modules.createTodo.view.ICreateTodoView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class CreateTodoPresenter extends BasePresenter<ICreateTodoView> {
    private TodoRepository repository;


    public CreateTodoPresenter(CreateTodoView view) {
        super(view);
        repository = new TodoRepository(view.getApplication());
    }

    @Override
    public void ready() {
        setupBackClickObservable();
        setupPriorityClickObservable();
    }

    private void setupBackClickObservable() {
        getView().getBackClickObservable()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        TodoEntity todoEntity = getView().getTodo();
                        if (isValidTodo(todoEntity)) {
                            saveTodo(todoEntity);
                        }
                        getView().dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setupPriorityClickObservable() {
        getView().getPriorityClickObservable()
                .debounce(100, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        getView().showPriorityChooser();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private boolean isValidTodo(TodoEntity todoEntity) {
        return (todoEntity.title != null && todoEntity.title.length() != 0)
                || (todoEntity.description != null && todoEntity.description.length() != 0);
    }

    private void saveTodo(TodoEntity todo) {
        repository.createTodo(todo);
    }

}
