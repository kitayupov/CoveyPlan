package com.kamtayupov.koviplan.data

import com.kamtayupov.koviplan.R
import org.joda.time.DateTime
import org.joda.time.Days

enum class DateRange(private val range: ClosedRange<Days>, val nameResId: Int) {
    PAST(Days.MIN_VALUE..Days.ZERO - 1, R.plurals.range_past),
    TODAY(Days.ZERO..Days.ZERO, R.plurals.range_today),
    WEEK(Days.ONE..Days.SIX, R.plurals.range_week),
    MONTH(Days.SEVEN..Days.days(28), R.plurals.range_month),
    YEAR(Days.days(29)..Days.days(365), R.plurals.range_year),
    OVER_A_YEAR(Days.days(366)..Days.MAX_VALUE, R.plurals.range_over_a_year);

    companion object {
        fun get(dateTime: DateTime): DateRange {
            val days = Days.daysBetween(DateTime.now(), dateTime)
            for (value in values()) {
                if (days in value.range) {
                    return value
                }
            }
            return PAST
        }
    }
}