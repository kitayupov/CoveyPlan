package com.kamtayupov.koviplan.list.adapter

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RatingBar
import android.widget.TextView
import com.kamtayupov.koviplan.MainActivity
import com.kamtayupov.koviplan.R
import com.kamtayupov.koviplan.data.DateRange
import com.kamtayupov.koviplan.data.Task
import com.kamtayupov.koviplan.repository.TasksViewModel
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Months
import org.joda.time.Weeks

class TaskAdapter(
    private val context: Context,
    private val callback: OnItemSelectedCallback<Task>
) : BaseAdapter<Task, TaskAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_list_item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, list[position], callback)
    }

    override fun getItemCount() = list.size

    class ViewHolder(view: View) : BaseViewHolder<Task>(view) {
        private val name = itemView.findViewById<TextView>(R.id.name_text)
        private val date = itemView.findViewById<TextView>(R.id.date_text)
        private val priority = itemView.findViewById<RatingBar>(R.id.priority_bar)
        private val done = itemView.findViewById<CheckBox>(R.id.done_check)

        fun bind(context: Context, task: Task, callback: OnItemSelectedCallback<Task>) {
            super.bind(task, callback)
            name.text = task.name
            date.text = getDateString(context, task)
            priority.rating = task.priority.value().toFloat()
            done.isChecked = task.done
            done.setOnCheckedChangeListener { _, checked ->
                run {
                    ViewModelProviders.of(context as MainActivity).get(TasksViewModel::class.java)
                        .updateItem(task, task.copy(done = checked))
                }
            }
        }

        private fun getDateString(context: Context, task: Task): String {
            if (task.dateTime == Task.DEFAULT_DATE_TIME) return task.toString()
            with(DateRange.get(task.dateTime)) {
                val count = when (this) {
                    DateRange.PAST -> -Days.daysBetween(DateTime.now(), task.dateTime).days
                    DateRange.MONTH -> Weeks.weeksBetween(DateTime.now(), task.dateTime).weeks
                    DateRange.YEAR -> Months.monthsBetween(DateTime.now(), task.dateTime).months
                    else -> Days.daysBetween(DateTime.now(), task.dateTime).days
                }
                return context.resources.getQuantityString(nameResId, count, count)
            }
        }
    }
}