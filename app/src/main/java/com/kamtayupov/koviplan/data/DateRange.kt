package com.kamtayupov.koviplan.data

import org.joda.time.DateTime
import org.joda.time.Days

enum class DateRange(private val range: ClosedRange<Days>) {
    PAST(Days.MIN_VALUE..Days.ZERO - 1),
    TODAY(Days.ZERO..Days.ZERO),
    WEEK(Days.ONE..Days.SIX),
    MONTH(Days.SEVEN..Days.days(28)),
    HALF_YEAR(Days.days(29)..Days.days(180)),
    YEAR(Days.days(181)..Days.days(365)),
    OVER_A_YEAR(Days.days(366)..Days.MAX_VALUE);

    companion object {
        fun get(dateTime: DateTime): DateRange {
            val days = Days.daysBetween(dateTime, DateTime.now())
            for (value in values()) {
                if (days in value.range) {
                    return value
                }
            }
            return PAST
        }
    }
}