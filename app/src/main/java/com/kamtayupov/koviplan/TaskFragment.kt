package com.kamtayupov.koviplan

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TaskFragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val list = arguments?.getStringArrayList(KEY_LIST) ?: return
        (view as RecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TaskAdapter(list)
        }
    }

    companion object {
        private const val KEY_LIST = "TaskFragment.KeyList"

        fun newInstance(list: ArrayList<String>): TaskFragment {
            return TaskFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(KEY_LIST, list)
                }
            }
        }
    }
}