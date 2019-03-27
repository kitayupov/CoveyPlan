package com.kamtayupov.koviplan.list

import android.support.v7.widget.RecyclerView
import com.kamtayupov.koviplan.data.Task

abstract class BaseTaskAdapter<T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {
    protected val list = ArrayList<Task>()

    fun setList(it: List<Task>) {
        list.apply {
            clear()
            addAll(it)
            notifyDataSetChanged()
        }
    }

    enum class Type {
        SIMPLE, USUAL
    }
}