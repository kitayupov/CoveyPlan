package com.kamtayupov.koviplan.editor

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.kamtayupov.koviplan.R
import com.kamtayupov.koviplan.data.Task
import com.kamtayupov.koviplan.databinding.FragmentEditTaskBinding
import com.kamtayupov.koviplan.repository.TasksViewModel
import org.joda.time.DateTime
import java.util.*

class EditTaskFragment : Fragment() {
    private lateinit var dateText: TextView
    private lateinit var priorityBar: RatingBar
    private lateinit var calendarImage: ImageView
    private lateinit var plusFab: FloatingActionButton
    private lateinit var saveFab: FloatingActionButton
    private lateinit var originalTask: Task
    private lateinit var editedTask: Task
    private lateinit var binding: FragmentEditTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.edit, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_task, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dateText = view.findViewById(R.id.task_date_text)
        calendarImage = view.findViewById(R.id.task_calendar_image)
        priorityBar = view.findViewById(R.id.task_priority_bar)
        val task = arguments?.getParcelable<Task>(KEY_TASK)
        activity?.title = task?.name ?: getString(R.string.title_edit_task)
        originalTask = if (task is Task) task else Task()
        editedTask = originalTask.copy()
        if (task is Task) binding.task = editedTask
        initFieldListeners()
        initFabs()
    }

    private fun initFieldListeners() {
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
        ViewModelProviders.of(activity!!).get(TasksViewModel::class.java).tasks?.value?.apply {
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
            putParcelable(KEY_TASK, task)
        }
    }
}