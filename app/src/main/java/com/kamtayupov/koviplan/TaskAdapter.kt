package com.kamtayupov.koviplan

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TaskAdapter(
    private val list: ArrayList<Task>,
    private val size: Size,
    private val listener: OnTaskSelectionListener
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutResId = when (size) {
            Size.SMALL -> R.layout.layout_list_item_task_small
            Size.NORMAL -> R.layout.layout_list_item_task
        }
        val view = inflater.inflate(layoutResId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun getItemCount() = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = itemView.findViewById<TextView>(R.id.name_text)

        fun bind(task: Task, listener: OnTaskSelectionListener) {
            name.text = task.name
            itemView.setOnClickListener {
                listener.onTaskSelected(task)
            }
        }
    }

    interface OnTaskSelectionListener {
        fun onTaskSelected(task: Task)
    }

    enum class Size {
        SMALL, NORMAL
    }
}