package com.kamtayupov.koviplan

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class EditTaskFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val task = arguments?.getSerializable(KEY_TASK) ?: return
        println(task as Task)
    }

    companion object {
        private const val KEY_TASK = "EditTaskFragment.KeyTask"

        fun newInstance(task: Task): EditTaskFragment {
            return EditTaskFragment().apply {
                arguments = getArguments(task)
            }
        }

        fun getArguments(task: Task) = Bundle().apply {
            putSerializable(KEY_TASK, task)
        }
    }
}