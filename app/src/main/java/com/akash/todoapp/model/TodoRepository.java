package com.akash.todoapp.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.akash.todoapp.model.database.TodoDao;
import com.akash.todoapp.model.database.TodoDatabase;
import com.akash.todoapp.model.database.TodoEntity;
import com.akash.todoapp.utils.RxAsyncTask;

import java.util.List;
import java.util.Optional;

import io.reactivex.disposables.Disposable;

public class TodoRepository {
    private TodoDao todoDao;
    private LiveData<List<TodoEntity>> allTodos;
    private static final String TAG = "TodoRepository";

    public TodoRepository(Application application) {
        TodoDatabase db = TodoDatabase.getDatabase(application);
        todoDao = db.todoDao();
        allTodos = todoDao.getAllTodos();
    }

    public LiveData<List<TodoEntity>> getAllTodos() {
        return allTodos;
    }

    public Disposable createTodo(TodoEntity entity) {
        return new InsertTodoTask(todoDao, entity)
                .returnObservable()
                .subscribe(
                        aVoid -> Log.e(TAG, "createTodo: Success"),
                        throwable -> Log.e(TAG, "createTodo: ", throwable)
                );
    }

    public Disposable deleteTodo(TodoEntity todoEntity) {
        return new DeleteTodoTask(todoDao, todoEntity)
                .returnObservable()
                .subscribe(
                        aVoid -> Log.e(TAG, "deleteTodo: Success"),
                        throwable -> Log.e(TAG, "deleteTodo: ", throwable)
                );
    }


    private static class InsertTodoTask extends RxAsyncTask<Integer> {
        private TodoEntity entity;
        private TodoDao todoDao;

        InsertTodoTask(TodoDao todoDao, TodoEntity entity) {
            this.entity = entity;
            this.todoDao = todoDao;
        }

        @Override
        public Integer run() throws Exception {
            todoDao.insert(entity);
            return 0;
        }
    }

    private static class DeleteTodoTask extends RxAsyncTask<Integer> {
        private TodoEntity entity;
        private TodoDao todoDao;

        DeleteTodoTask(TodoDao todoDao, TodoEntity entity) {
            this.entity = entity;
            this.todoDao = todoDao;
        }

        @Override
        public Integer run() throws Exception {
            todoDao.delete(entity);
            return 0;
        }
    }
}
