package com.kamtayupov.koviplan.editor

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.kamtayupov.koviplan.R
import com.kamtayupov.koviplan.data.Priority
import com.kamtayupov.koviplan.data.Task
import com.kamtayupov.koviplan.repository.TasksViewModel
import org.joda.time.DateTime
import java.util.*

class EditTaskFragment : Fragment() {
    private lateinit var nameText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var dateText: TextView
    private lateinit var priorityBar: RatingBar
    private lateinit var calendarImage: ImageView
    private lateinit var plusFab: FloatingActionButton
    private lateinit var saveFab: FloatingActionButton
    private lateinit var originalTask: Task
    private lateinit var editedTask: Task

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        nameText = view.findViewById(R.id.task_name_text)
        descriptionText = view.findViewById(R.id.task_description_text)
        dateText = view.findViewById(R.id.task_date_text)
        calendarImage = view.findViewById(R.id.task_calendar_image)
        priorityBar = view.findViewById(R.id.task_priority_bar)
        val task = arguments?.getSerializable(KEY_TASK)
        activity?.apply {
            if (task is Task) {
                title = task.name
            } else {
                setTitle(R.string.title_edit_task)
            }
        }
        originalTask = if (task is Task) task else Task()
        editedTask = originalTask.copy()
        updateFields()
        plusFab = activity?.findViewById(R.id.plus_fab) ?: return
        saveFab = activity?.findViewById(R.id.save_fab) ?: return
        plusFab.hide(getVisibilityChangedListener(saveFab))
        for (v in arrayOf(dateText, calendarImage)) {
            v.setOnClickListener {
                val dialog =
                    if (editedTask.dateTime == Task.DEFAULT_DATE_TIME) DatePickerDialogFragment()
                    else DatePickerDialogFragment.newInstance(editedTask.dateTime.toDate())
                dialog.callback = object : DatePickerDialogFragment.Callback {
                    override fun onDatePicked(date: Date) {
                        editedTask.dateTime = DateTime(date)
                        dateText.text = editedTask.dateTime.toLocalDate().toString()
                    }
                }
                dialog.show(activity?.supportFragmentManager, "DatePicker")
            }
        }
    }

    private fun updateFields() {
        nameText.text = editedTask.name
        descriptionText.text = editedTask.description
        dateText.text = when (editedTask.dateTime) {
            Task.DEFAULT_DATE_TIME -> null
            else -> editedTask.dateTime.toLocalDate().toString()
        }
        priorityBar.rating = editedTask.priority.value().toFloat()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveFab.hide(getVisibilityChangedListener(plusFab))
    }

    fun saveTask() {
        editedTask.apply {
            name = nameText.text.toString()
            description = descriptionText.text.toString()
            priority = Priority.get(priorityBar.rating.toInt())
        }
        TasksViewModel.tasks?.value?.apply {
            with(indexOf(originalTask)) {
                if (this == -1) add(editedTask) else set(this, editedTask)
            }
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