package com.kamtayupov.koviplan.list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamtayupov.koviplan.MainActivity
import com.kamtayupov.koviplan.R
import com.kamtayupov.koviplan.data.Importance
import com.kamtayupov.koviplan.data.Task
import com.kamtayupov.koviplan.data.Urgency
import com.kamtayupov.koviplan.repository.TasksViewModel

class TaskFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments ?: return
        val type = arguments!!.getSerializable(KEY_LIST_TYPE)
        val simple = arguments!!.getBoolean(KEY_SIMPLE)
        val recyclerView = view as RecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = if (simple) {
                TaskSimpleAdapter(object : TaskSimpleAdapter.OnClickCallback {
                    override fun onSelect() {
                        (activity as MainActivity).onChapterSelected(type as TaskType)
                    }
                })
            } else {
                TaskAdapter(context, object : TaskAdapter.OnTaskSelectedCallback {
                    override fun onSelected(task: Task) {
                        (activity as MainActivity).onTaskSelected(task)
                    }
                })
            }
        }
        TasksViewModel.tasks?.observe(this, Observer {
            with(type as TaskType) {
                it?.filter {
                    Importance.get(it.priority) == this.importance && Urgency.get(it.dateTime) == this.urgency
                }?.let {
                    when (type.urgency) {
                        Urgency.URGENT -> it.sortedBy { it.dateTime }
                        Urgency.NORMAL -> it.sortedByDescending { it.priority }
                    }.apply {
                        (recyclerView.adapter as BaseTaskAdapter).setList(this)
                    }
                }
            }
        })
        if (!simple) activity?.setTitle((type as TaskType).nameResId)
    }

    companion object {
        private const val KEY_LIST_TYPE = "TaskFragment.KeyListType"
        private const val KEY_SIMPLE = "TaskFragment.KeySimple"

        fun newInstance(taskType: TaskType, simple: Boolean): TaskFragment {
            return TaskFragment().apply {
                arguments = getArguments(taskType, simple)
            }
        }

        fun getArguments(taskType: TaskType, simple: Boolean) = Bundle().apply {
            putSerializable(KEY_LIST_TYPE, taskType)
            putBoolean(KEY_SIMPLE, simple)
        }
    }

    enum class TaskType(val urgency: Urgency, val importance: Importance, val nameResId: Int) {
        URGENT_IMPORTANT(Urgency.URGENT, Importance.IMPORTANT, R.string.title_type_urgent_important),
        URGENT_UNIMPORTANT(Urgency.URGENT, Importance.NORMAL, R.string.title_type_urgent_unimportant),
        NON_URGENT_IMPORTANT(Urgency.NORMAL, Importance.IMPORTANT, R.string.title_type_non_urgent_important),
        NON_URGENT_UNIMPORTANT(Urgency.NORMAL, Importance.NORMAL, R.string.title_type_non_urgent_unimportant)
    }
}
