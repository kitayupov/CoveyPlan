package com.kamtayupov.koviplan

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TaskFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val list: ArrayList<Task> = arguments?.getSerializable(KEY_TASK_LIST) as ArrayList<Task>
        (view as RecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TaskAdapter(list)
        }
    }

    companion object {
        private const val KEY_TASK_LIST = "TaskFragment.KeyTaskList"

        fun newInstance(list: ArrayList<Task>): TaskFragment {
            return TaskFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_TASK_LIST, list)
                }
            }
        }
    }
}