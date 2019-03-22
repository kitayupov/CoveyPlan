package com.kamtayupov.koviplan

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class EditTaskFragment : Fragment() {

    private lateinit var nameTextView: TextView
    private lateinit var descriptionTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val task = arguments?.getSerializable(KEY_TASK)
        if (task is Task) {
            nameTextView = view.findViewById(R.id.task_name_text)
            descriptionTextView = view.findViewById(R.id.task_description_text)
            nameTextView.text = task.name
            descriptionTextView.text = task.description
            activity?.title = task.name
        } else {
            activity?.setTitle(R.string.title_edit_task)
        }
    }

    companion object {
        private const val KEY_TASK = "EditTaskFragment.KeyTask"

        fun getArguments(task: Task) = Bundle().apply {
            putSerializable(KEY_TASK, task)
        }
    }
}