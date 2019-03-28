package com.kamtayupov.koviplan.room

import android.arch.persistence.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskEntity")
    fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    fun getById(id: Long): TaskEntity

    @Insert
    fun insert(taskEntity: TaskEntity)

    @Update
    fun update(taskEntity: TaskEntity)

    @Delete
    fun delete(taskEntity: TaskEntity)
}