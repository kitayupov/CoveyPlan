package com.kamtayupov.koviplan.data

enum class Urgency(private val dateRanges: Array<DateRange>) {
    EXPIRED(arrayOf(DateRange.PAST)),
    URGENT(arrayOf(DateRange.TODAY, DateRange.WEEK)),
    NORMAL(DateRange.values().filter {
        !com.kamtayupov.koviplan.data.Urgency.EXPIRED.dateRanges.contains(it) &&
                !com.kamtayupov.koviplan.data.Urgency.URGENT.dateRanges.contains(it)
    }.toTypedArray())
}