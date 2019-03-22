package com.kamtayupov.koviplan.repository

import com.kamtayupov.koviplan.data.Priority
import com.kamtayupov.koviplan.data.Task

interface RepositoryIF {
    fun getTasks() : List<Task>
    fun getTasks(priority: Priority) : List<Task>
}