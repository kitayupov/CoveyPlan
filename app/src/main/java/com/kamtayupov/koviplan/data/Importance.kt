package com.kamtayupov.koviplan.data

enum class Importance(private val priorities: Array<Priority>) {
    IMPORTANT(arrayOf(Priority.SERIOUS, Priority.IMPORTANT, Priority.CRITICAL)),
    NORMAL(Priority.values().filter {
        !IMPORTANT.priorities.contains(it)
    }.toTypedArray());

    companion object {
        fun get(priority: Priority): Importance {
            for (value in values()) {
                if (priority in value.priorities) {
                    return value
                }
            }
            return NORMAL
        }
    }
}