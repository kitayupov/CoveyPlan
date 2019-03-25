package com.kamtayupov.koviplan.repository

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import com.kamtayupov.koviplan.data.Priority
import com.kamtayupov.koviplan.data.Task
import org.joda.time.DateTime
import org.joda.time.Days
import java.util.*

class Repository {
    companion object LifecycleObserver {
        val tasks = MutableLiveData<List<Task>>().apply {
            postValue(ArrayList<Task>().apply {
                for (c in 0..1000) {
                    add(
                        Task(
                            "Task $c",
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                            dateTime = (DateTime.now() + Days.days((Math.random() * 400 - 7).toInt())),
                            priority = Priority.values()[(Math.random() * Priority.values().size).toInt()]
                        )
                    )
                }
            })
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun connect() {

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun disconnect() {

        }
    }
}