package com.kamtayupov.koviplan.editor

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.DatePicker
import com.kamtayupov.koviplan.R
import java.util.*

class DatePickerDialogFragment : DialogFragment() {
    private lateinit var datePicker: DatePicker
    var callback: Callback? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
            .setView(R.layout.dialog_calendar)
            .setPositiveButton(android.R.string.ok, listener)
            .setNeutralButton(android.R.string.cancel, listener)
            .create()
    }

    override fun onStart() {
        super.onStart()
        datePicker = dialog.findViewById(R.id.date_picker)
        val date = arguments?.getSerializable(KEY_DATE) ?: return
        if (date is Date) {
            datePicker.updateDate(1900 + date.year, date.month, date.date)
        }
    }

    private val listener = DialogInterface.OnClickListener { dialog, which ->
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> callback?.onDatePicked(getDate())
        }
        dialog.dismiss()
    }

    private fun getDate() = Date().apply {
        year = datePicker.year - 1900
        month = datePicker.month
        date = datePicker.dayOfMonth
        hours = 0
        minutes = 0
        seconds = 0
    }

    companion object {
        private const val KEY_DATE = "DatePickerDialogFragment.KeyDate"

        fun newInstance(date: Date): DatePickerDialogFragment {
            return DatePickerDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_DATE, date)
                }
            }
        }
    }

    interface Callback {
        fun onDatePicked(date: Date)
    }
}