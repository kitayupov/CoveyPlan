package com.kamtayupov.koviplan.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kamtayupov.koviplan.R
import com.kamtayupov.koviplan.data.Task

class TaskSimpleAdapter(private val callback: OnItemSelectedCallback<Task>) :
    BaseAdapter<Task, TaskSimpleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_list_item_task_small, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], callback)
    }

    override fun getItemCount() = list.size

    class ViewHolder(view: View) : BaseViewHolder<Task>(view) {
        private val name = itemView.findViewById<TextView>(R.id.name_text)

        override fun bind(item: Task, callback: OnItemSelectedCallback<Task>) {
            super.bind(item, callback)
            name.text = item.name
        }
    }
}