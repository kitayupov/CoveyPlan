package com.kamtayupov.koviplan.editor

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.kamtayupov.koviplan.R
import com.kamtayupov.koviplan.data.Priority
import com.kamtayupov.koviplan.data.Task
import com.kamtayupov.koviplan.repository.Repository

class EditTaskFragment : Fragment() {
    private lateinit var nameText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var dateText: TextView
    private lateinit var priorityBar: RatingBar
    private lateinit var plusFab: FloatingActionButton
    private lateinit var saveFab: FloatingActionButton
    private lateinit var task: Task

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        nameText = view.findViewById(R.id.task_name_text)
        descriptionText = view.findViewById(R.id.task_description_text)
        dateText = view.findViewById(R.id.task_date_text)
        priorityBar = view.findViewById(R.id.task_priority_bar)
        val task = arguments?.getSerializable(KEY_TASK)
        if (task is Task) {
            this.task = task
            nameText.text = task.name
            descriptionText.text = task.description
            dateText.text = when (task.dateTime) {
                Task.DEFAULT_DATE_TIME -> null
                else -> task.dateTime.toLocalDateTime().toString()
            }
            priorityBar.rating = task.priority.value().toFloat()
            activity?.title = task.name
        } else {
            this.task = Task()
            activity?.setTitle(R.string.title_edit_task)
        }
        plusFab = activity?.findViewById(R.id.plus_fab) ?: return
        saveFab = activity?.findViewById(R.id.save_fab) ?: return
        plusFab.hide(getVisibilityChangedListener(saveFab))
    }

    override fun onDestroy() {
        super.onDestroy()
        saveFab.hide(getVisibilityChangedListener(plusFab))
    }

    fun saveTask() {
        val editTask = Task(
            name = nameText.text.toString(),
            description = descriptionText.text.toString(),
            priority = Priority.get(priorityBar.rating.toInt()),
            dateTime = task.dateTime
        )
        Repository.tasks.value?.apply {
            val index = indexOf(task)
            if (index == -1) add(editTask) else set(index, editTask)
        }
    }

    private fun getVisibilityChangedListener(nextFab: FloatingActionButton): FloatingActionButton.OnVisibilityChangedListener {
        return object : FloatingActionButton.OnVisibilityChangedListener() {
            override fun onHidden(fab: FloatingActionButton?) {
                nextFab.show()
            }
        }
    }

    companion object {
        private const val KEY_TASK = "EditTaskFragment.KeyTask"

        fun getArguments(task: Task) = Bundle().apply {
            putSerializable(KEY_TASK, task)
        }
    }
}