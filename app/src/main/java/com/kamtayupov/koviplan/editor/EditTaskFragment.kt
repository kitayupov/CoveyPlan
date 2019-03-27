package com.kamtayupov.koviplan.editor

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.edit, menu)
    }

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
        initFieldListeners()
        initFabs()
    }

    private fun updateFields() {
        nameText.text = editedTask.name
        descriptionText.text = editedTask.description
        dateText.text = editedTask.dateString()
        priorityBar.rating = editedTask.priority.value().toFloat()
    }

    private fun initFieldListeners() {
        nameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                editedTask.name = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        descriptionText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                editedTask.description = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        priorityBar.setOnRatingBarChangeListener { _, rating, _ -> editedTask.priority = Priority.get(rating.toInt()) }
        for (v in arrayOf(dateText, calendarImage)) {
            v.setOnClickListener {
                val dialog =
                    if (editedTask.dateTime == Task.DEFAULT_DATE_TIME) DatePickerDialogFragment()
                    else DatePickerDialogFragment.newInstance(editedTask.dateTime.toDate())
                dialog.callback = object : DatePickerDialogFragment.Callback {
                    override fun onDatePicked(date: Date) {
                        editedTask.dateTime = DateTime(date)
                        dateText.text = editedTask.dateString()
                    }
                }
                dialog.show(activity?.supportFragmentManager, "DatePicker")
            }
        }
    }

    private fun initFabs() {
        plusFab = activity?.findViewById(R.id.plus_fab) ?: return
        saveFab = activity?.findViewById(R.id.save_fab) ?: return
        plusFab.hide(getVisibilityChangedListener(saveFab))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.done -> {
                editedTask.done = true
                saveTask()
                activity?.supportFragmentManager?.popBackStack()
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        saveFab.hide(getVisibilityChangedListener(plusFab))
    }

    fun saveTask() {
        TasksViewModel.tasks?.value?.apply {
            with(indexOf(originalTask)) {
                if (this == -1) add(editedTask) else set(this, editedTask)
            }
        }
    }

    private fun getVisibilityChangedListener(nextFab: FloatingActionButton) =
        object : FloatingActionButton.OnVisibilityChangedListener() {
            override fun onHidden(fab: FloatingActionButton?) {
                nextFab.show()
            }
        }

    companion object {
        private const val KEY_TASK = "EditTaskFragment.KeyTask"

        fun getArguments(task: Task) = Bundle().apply {
            putSerializable(KEY_TASK, task)
        }
    }
}