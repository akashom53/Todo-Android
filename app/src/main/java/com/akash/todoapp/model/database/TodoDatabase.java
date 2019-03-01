package com.akash.todoapp.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {TodoEntity.class}, version = 1)
public abstract class TodoDatabase extends RoomDatabase {

    public abstract TodoDao todoDao();

    private static volatile TodoDatabase INSTANCE;

    public static TodoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoDatabase.class, "todo_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
