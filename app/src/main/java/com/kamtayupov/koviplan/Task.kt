package com.kamtayupov.koviplan

import java.io.Serializable
import java.util.*

data class Task(
    var name: String = "",
    var description: String = "",
    var date: Date = DEFAULT_DATE,
    var priority: Priority = Priority.DEFAULT
) : Serializable {

    companion object {
        val DEFAULT_DATE = Date(-1)
    }
}

enum class Priority {
    USELESS,
    DEFAULT,
    NORMAL,
    SERIOUS,
    IMPORTANT,
    CRITICAL;

    fun value() = ordinal
}