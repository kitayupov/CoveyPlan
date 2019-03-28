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
import com.kamtayupov.koviplan.list.adapter.BaseAdapter
import com.kamtayupov.koviplan.list.adapter.TaskAdapter
import com.kamtayupov.koviplan.list.adapter.TaskSimpleAdapter
import com.kamtayupov.koviplan.repository.TasksViewModel

class TaskFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments ?: return
        val type = arguments!!.getSerializable(KEY_LIST_TYPE)
        val simple = arguments!!.getBoolean(KEY_SIMPLE)
        val completed = arguments!!.getBoolean(KEY_COMPLETED)
        val recyclerView = view as RecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = if (simple) {
                TaskSimpleAdapter(object : BaseAdapter.OnItemSelectedCallback<Task> {
                    override fun onSelected(item: Task) {
                        if (activity is MainActivity && type is TaskType) {
                            (activity as MainActivity).onChapterSelected(type)
                        }
                    }
                })
            } else {
                TaskAdapter(
                    context!!,
                    object : BaseAdapter.OnItemSelectedCallback<Task> {
                        override fun onSelected(item: Task) {
                            if (activity is MainActivity) {
                                (activity as MainActivity).onTaskSelected(item)
                            }
                        }
                    })
            }
        }
        TasksViewModel.tasks?.observe(this, Observer {
            if (type is TaskType) {
                it?.filter {
                    Importance.get(it.priority) == type.importance &&
                            Urgency.get(it.dateTime) == type.urgency &&
                            it.done == completed
                }?.let {
                    when (type.urgency) {
                        Urgency.URGENT -> it.sortedBy { it.dateTime }
                        Urgency.NORMAL -> it.sortedByDescending { it.priority }
                    }
                }
            } else {
                it?.filter { it.done == completed }?.sortedBy { it.dateTime }
            }?.apply { (recyclerView.adapter as BaseAdapter<Task, *>).list = ArrayList(this) }
        })
        if (type is TaskType) {
            if (!simple) activity?.setTitle(type.nameResId)
        } else {
            if (completed) activity?.setTitle(R.string.nav_menu_completed)
        }
    }

    companion object {
        private const val KEY_LIST_TYPE = "TaskFragment.KeyListType"
        private const val KEY_SIMPLE = "TaskFragment.KeySimple"
        private const val KEY_COMPLETED = "TaskFragment.KeyCompleted"

        fun newInstance(taskType: TaskType? = null, simple: Boolean = false, completed: Boolean = false): TaskFragment {
            return TaskFragment().apply {
                arguments = getArguments(taskType, simple, completed)
            }
        }

        fun getArguments(taskType: TaskType? = null, simple: Boolean = false, completed: Boolean = false) =
            Bundle().apply {
                putSerializable(KEY_LIST_TYPE, taskType)
                putBoolean(KEY_SIMPLE, simple)
                putBoolean(KEY_COMPLETED, completed)
            }
    }

    enum class TaskType(val urgency: Urgency, val importance: Importance, val nameResId: Int) {
        URGENT_IMPORTANT(Urgency.URGENT, Importance.IMPORTANT, R.string.title_type_urgent_important),
        URGENT_UNIMPORTANT(Urgency.URGENT, Importance.NORMAL, R.string.title_type_urgent_unimportant),
        NON_URGENT_IMPORTANT(Urgency.NORMAL, Importance.IMPORTANT, R.string.title_type_non_urgent_important),
        NON_URGENT_UNIMPORTANT(Urgency.NORMAL, Importance.NORMAL, R.string.title_type_non_urgent_unimportant)
    }
}
