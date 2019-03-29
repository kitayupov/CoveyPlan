package com.kamtayupov.koviplan.room

import android.arch.persistence.room.*
import com.kamtayupov.koviplan.data.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAll(): List<Task>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getById(id: Long): Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskEntity: Task)

    @Update
    fun update(taskEntity: Task)

    @Delete
    fun delete(taskEntity: Task)
}