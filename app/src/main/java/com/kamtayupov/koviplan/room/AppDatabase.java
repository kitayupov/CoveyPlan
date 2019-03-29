package com.kamtayupov.koviplan.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.kamtayupov.koviplan.data.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}