package com.kamtayupov.koviplan.list.adapter

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import com.kamtayupov.koviplan.MainActivity
import com.kamtayupov.koviplan.R
import com.kamtayupov.koviplan.data.Task
import com.kamtayupov.koviplan.databinding.LayoutListItemTaskBinding
import com.kamtayupov.koviplan.repository.TasksViewModel

class TaskAdapter(
    private val context: Context,
    private val callback: OnItemSelectedCallback<Task>
) : BaseAdapter<Task, TaskAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<LayoutListItemTaskBinding>(inflater, R.layout.layout_list_item_task, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, list[position], callback)
    }

    override fun getItemCount() = list.size

    class ViewHolder(private val binding: LayoutListItemTaskBinding) : BaseViewHolder<Task>(binding.root) {
        private val done = itemView.findViewById<CheckBox>(R.id.done_check)

        fun bind(context: Context, task: Task, callback: OnItemSelectedCallback<Task>) {
            super.bind(task, callback)
            binding.task = task
            binding.executePendingBindings()
            done.setOnCheckedChangeListener { _, checked ->
                run {
                    ViewModelProviders.of(context as MainActivity).get(TasksViewModel::class.java)
                        .updateItem(task, task.copy(done = checked))
                }
            }
        }
    }
}