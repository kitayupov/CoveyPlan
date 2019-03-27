package com.kamtayupov.koviplan.repository

import com.kamtayupov.koviplan.data.Priority
import com.kamtayupov.koviplan.data.Task
import org.joda.time.DateTime
import org.joda.time.Days

class Repository {
    companion object {
        fun load(callback: Callback) {
            Thread {
                callback.onLoaded(
                    ArrayList<Task>().apply {
                        for (c in 0..1000) {
                            add(
                                Task(
                                    "Task $c",
                                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                                    dateTime = (DateTime.now() + Days.days((Math.random() * 400 - 7).toInt())),
                                    priority = Priority.values()[(Math.random() * Priority.values().size).toInt()],
                                    done = Math.random() > 0.8
                                )
                            )
                        }
                    }
                )
            }.start()
        }
    }

    interface Callback {
        fun onLoaded(list: MutableList<Task>)
    }
}