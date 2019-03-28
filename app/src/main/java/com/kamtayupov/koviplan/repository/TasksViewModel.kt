package com.kamtayupov.koviplan.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kamtayupov.koviplan.data.Task

class TasksViewModel : ViewModel() {
    var tasks: MutableLiveData<MutableList<Task>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                load()
            }
            return field
        }

    private fun load() {
        Repository.load(object : Repository.Callback {
            override fun onLoaded(list: MutableList<Task>) {
                tasks?.postValue(list)
            }
        })
    }

    fun updateItem(task: Task, with: Task) {
        tasks?.let {
            it.postValue(
                it.value?.apply {
                    val index = indexOf(task)
                    if (index != -1) this[index] = with
                }
            )
        }
    }
}
