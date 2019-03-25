package com.kamtayupov.koviplan.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.kamtayupov.koviplan.R
import com.kamtayupov.koviplan.data.Task

class TaskAdapter(
    private val size: Size,
    private val listener: OnTaskSelectionListener
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private val list = ArrayList<Task>()

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
        holder.bind(list[position], size, listener)
    }

    override fun getItemCount() = list.size

    fun setList(it: List<Task>) {
        list.apply {
            clear()
            addAll(it)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = itemView.findViewById<TextView>(R.id.name_text)
        private val date = itemView.findViewById<TextView>(R.id.date_text)
        private val priority = itemView.findViewById<RatingBar>(R.id.priority_bar)

        fun bind(task: Task, size: Size, listener: OnTaskSelectionListener) {
            name.text = task.name
            if (size == Size.NORMAL) {
                date.text = task.dateTime.toLocalDate().toString()
                priority.rating = task.priority.value().toFloat()
            }
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