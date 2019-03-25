package com.kamtayupov.koviplan.data

import org.joda.time.DateTime

enum class Urgency(private val dateRanges: Array<DateRange>) {
    URGENT(arrayOf(DateRange.PAST, DateRange.TODAY, DateRange.WEEK)),
    NORMAL(DateRange.values().filter {
        !URGENT.dateRanges.contains(it)
    }.toTypedArray());

    companion object {
        fun get(dateTime: DateTime): Urgency {
            for (value in values()) {
                if (DateRange.get(dateTime) in value.dateRanges) {
                    return value
                }
            }
            return NORMAL
        }
    }
}