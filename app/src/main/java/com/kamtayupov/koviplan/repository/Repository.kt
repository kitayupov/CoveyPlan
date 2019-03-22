package com.kamtayupov.koviplan.repository

import com.kamtayupov.koviplan.data.Priority
import com.kamtayupov.koviplan.data.Task
import java.util.*

class Repository {
    companion object : RepositoryIF {
        override fun getTasks(): List<Task> {
            return ArrayList<Task>().apply {
                for (c in 'a'..'z') {
                    add(
                        Task(
                            c.toString(),
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                            priority = Priority.values()[(Math.random() * Priority.values().size).toInt()]
                        )
                    )
                }
            }
        }

        override fun getTasks(priority: Priority): List<Task> {
            return ArrayList<Task>().apply {
                for (c in 'a'..'z') {
                    add(
                        Task(
                            c.toString(),
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                            priority = priority
                        )
                    )
                }
            }
        }
    }
}