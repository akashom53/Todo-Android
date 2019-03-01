package com.akash.todoapp.model.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

@Entity(tableName = "TODO_table")
public class TodoEntity {
    public enum Priority {
        HIGH(0),
        MEDIUM(1),
        LOW(2);

        private int level;

        Priority(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    public String description;

    public boolean completed;

    @TypeConverters(PriorityConverter.class)
    public Priority priority;
}
