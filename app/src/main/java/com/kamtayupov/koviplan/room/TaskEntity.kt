package com.kamtayupov.koviplan.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.kamtayupov.koviplan.data.Priority
import com.kamtayupov.koviplan.data.Task

@Entity
data class TaskEntity(
    @PrimaryKey
    var id: Long,
    var name: String = "",
    var description: String = "",
    var dateTime: Long = Task.DEFAULT_DATE_TIME.millis,
    var priority: Int = Priority.DEFAULT.value(),
    var done: Boolean = false
)