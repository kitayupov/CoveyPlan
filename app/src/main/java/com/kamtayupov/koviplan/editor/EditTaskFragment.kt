package com.kamtayupov.koviplan.editor

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.kamtayupov.koviplan.R
import com.kamtayupov.koviplan.data.Task

class EditTaskFragment : Fragment() {

    private lateinit var nameText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var dateText: TextView
    private lateinit var priorityBar: RatingBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val task = arguments?.getSerializable(KEY_TASK)
        if (task is Task) {
            nameText = view.findViewById(R.id.task_name_text)
            descriptionText = view.findViewById(R.id.task_description_text)
            dateText = view.findViewById(R.id.task_date_text)
            priorityBar = view.findViewById(R.id.task_priority_bar)
            nameText.text = task.name
            descriptionText.text = task.description
            dateText.text = when (task.date) {
                Task.DEFAULT_DATE -> null
                else -> toString()
            }
            priorityBar.rating = task.priority.value().toFloat()
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