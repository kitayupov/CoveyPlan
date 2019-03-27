package com.kamtayupov.koviplan.data

import org.joda.time.DateTime
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class Task(
    var name: String = "",
    var description: String = "",
    var dateTime: DateTime = DEFAULT_DATE_TIME,
    var priority: Priority = Priority.UNKNOWN,
    var done: Boolean = false
) : Serializable {
    companion object {
        val DEFAULT_DATE_TIME = DateTime(-1)
    }

    fun dateString() = when (dateTime) {
        DEFAULT_DATE_TIME -> null
        else -> SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault()).format(dateTime.toDate())
    }
}