package com.akash.todoapp.model.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insert(TodoEntity todo);

    @Query("SELECT * from TODO_table ORDER by id DESC")
    LiveData<List<TodoEntity>> getAllTodos();

    @Delete
    void delete(TodoEntity todo);
}
