package com.akash.todoapp.model.database;

import android.arch.persistence.room.TypeConverter;

public class PriorityConverter {

    @TypeConverter
    public static TodoEntity.Priority toLevel(int level) {
        switch (level) {
            case 0:
                return TodoEntity.Priority.HIGH;
            case 1:
                return TodoEntity.Priority.MEDIUM;
            case 2:
                return TodoEntity.Priority.LOW;
            default:
                return TodoEntity.Priority.LOW;
        }
    }

    @TypeConverter
    public static int toInt(TodoEntity.Priority priority) {
        return priority.getLevel();
    }
}
