package com.kamtayupov.koviplan.data

import org.joda.time.DateTime
import java.io.Serializable

data class Task(
    var name: String = "",
    var description: String = "",
    var dateTime: DateTime = DEFAULT_DATE_TIME,
    var priority: Priority = Priority.UNKNOWN
) : Serializable {
    companion object {
        val DEFAULT_DATE_TIME = DateTime(-1)
    }
}