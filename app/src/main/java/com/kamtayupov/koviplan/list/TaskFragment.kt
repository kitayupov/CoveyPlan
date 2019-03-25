package com.kamtayupov.koviplan.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamtayupov.koviplan.MainActivity
import com.kamtayupov.koviplan.R
import com.kamtayupov.koviplan.list.TaskAdapter.Size.NORMAL
import com.kamtayupov.koviplan.list.TaskAdapter.Size.SMALL
import com.kamtayupov.koviplan.data.Task

class TaskFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val list = arguments?.getSerializable(KEY_TASK_LIST) ?: return
        val type = arguments?.getSerializable(KEY_LIST_TYPE) ?: return
        val size = arguments?.getSerializable(KEY_LIST_SIZE) ?: return
        (view as RecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TaskAdapter(
                list as ArrayList<Task>,
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
        when (size as TaskAdapter.Size) {
            NORMAL -> activity?.setTitle((type as TaskType).nameResId)
            else -> {
            }
        }
    }

    companion object {
        private const val KEY_TASK_LIST = "TaskFragment.KeyTaskList"
        private const val KEY_LIST_TYPE = "TaskFragment.KeyListType"
        private const val KEY_LIST_SIZE = "TaskFragment.KeyListSize"

        fun newInstance(list: ArrayList<Task>, taskType: TaskType, size: TaskAdapter.Size): TaskFragment {
            return TaskFragment().apply {
                arguments = getArguments(list, taskType, size)
            }
        }

        fun getArguments(list: ArrayList<Task>, taskType: TaskType, size: TaskAdapter.Size) = Bundle().apply {
            putSerializable(KEY_TASK_LIST, list)
            putSerializable(KEY_LIST_TYPE, taskType)
            putSerializable(KEY_LIST_SIZE, size)
        }
    }

    enum class TaskType(val nameResId: Int) {
        URGENT_IMPORTANT(R.string.title_type_urgent_important),
        URGENT_UNIMPORTANT(R.string.title_type_urgent_unimportant),
        NON_URGENT_IMPORTANT(R.string.title_type_non_urgent_important),
        NON_URGENT_UNIMPORTANT(R.string.title_type_non_urgent_unimportant)
    }
}