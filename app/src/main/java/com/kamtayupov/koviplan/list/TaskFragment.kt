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
import com.kamtayupov.koviplan.list.TaskAdapter.Size.NORMAL
import com.kamtayupov.koviplan.list.TaskAdapter.Size.SMALL
import com.kamtayupov.koviplan.repository.Repository

class TaskFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val type = arguments?.getSerializable(KEY_LIST_TYPE) ?: return
        val size = arguments?.getSerializable(KEY_LIST_SIZE) ?: return
        val recyclerView = view as RecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TaskAdapter(
                size as TaskAdapter.Size,
                object : TaskAdapter.OnTaskSelectionListener {
                    override fun onTaskSelected(task: Task) {
                        (activity as MainActivity).apply {
                            when (size) {
                                SMALL -> onChapterSelected(type as TaskType)
                                NORMAL -> onTaskSelected(task)
                            }
                        }
                    }
                }
            )
        }
        Repository.tasks.observe(this, Observer {
            with(type as TaskType) {
                it?.filter {
                    Importance.get(it.priority) == this.importance && Urgency.get(it.dateTime) == this.urgency
                }?.let {
                    when (type.urgency) {
                        Urgency.URGENT -> it.sortedBy { it.dateTime }
                        Urgency.NORMAL -> it.sortedByDescending { it.priority }
                    }.apply {
                        (recyclerView.adapter as TaskAdapter).setList(this)
                    }
                }
            }
        })
        when (size as TaskAdapter.Size) {
            NORMAL -> activity?.setTitle((type as TaskType).nameResId)
            else -> {
            }
        }
    }

    companion object {
        private const val KEY_LIST_TYPE = "TaskFragment.KeyListType"
        private const val KEY_LIST_SIZE = "TaskFragment.KeyListSize"

        fun newInstance(taskType: TaskType, size: TaskAdapter.Size): TaskFragment {
            return TaskFragment().apply {
                arguments = getArguments(taskType, size)
            }
        }

        fun getArguments(taskType: TaskType, size: TaskAdapter.Size) = Bundle().apply {
            putSerializable(KEY_LIST_TYPE, taskType)
            putSerializable(KEY_LIST_SIZE, size)
        }
    }

    enum class TaskType(val urgency: Urgency, val importance: Importance, val nameResId: Int) {
        URGENT_IMPORTANT(Urgency.URGENT, Importance.IMPORTANT, R.string.title_type_urgent_important),
        URGENT_UNIMPORTANT(Urgency.URGENT, Importance.NORMAL, R.string.title_type_urgent_unimportant),
        NON_URGENT_IMPORTANT(Urgency.NORMAL, Importance.IMPORTANT, R.string.title_type_non_urgent_important),
        NON_URGENT_UNIMPORTANT(Urgency.NORMAL, Importance.NORMAL, R.string.title_type_non_urgent_unimportant)
    }
}
