package com.kamtayupov.koviplan.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kamtayupov.koviplan.R
import com.kamtayupov.koviplan.data.Task

class TaskSimpleAdapter(private val callback: OnClickCallback) : BaseTaskAdapter<TaskSimpleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_list_item_task_small, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], callback)
    }

    override fun getItemCount() = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = itemView.findViewById<TextView>(R.id.name_text)

        fun bind(task: Task, callback: OnClickCallback) {
            name.text = task.name
            itemView.setOnClickListener { callback.onSelect() }
        }
    }

    interface OnClickCallback {
        fun onSelect()
    }
}