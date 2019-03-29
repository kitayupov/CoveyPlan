package com.kamtayupov.koviplan.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import android.content.Context
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Months
import org.joda.time.Weeks
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "tasks")
@TypeConverters(Task.TypeConverters::class)
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
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

    fun getDateString(context: Context): String {
        if (dateTime == Task.DEFAULT_DATE_TIME) return toString()
        with(DateRange.get(dateTime)) {
            val count = when (this) {
                DateRange.PAST -> -Days.daysBetween(DateTime.now(), dateTime).days
                DateRange.MONTH -> Weeks.weeksBetween(DateTime.now(), dateTime).weeks
                DateRange.YEAR -> Months.monthsBetween(DateTime.now(), dateTime).months
                else -> Days.daysBetween(DateTime.now(), dateTime).days
            }
            return context.resources.getQuantityString(nameResId, count, count)
        }
    }

    class TypeConverters {
        @TypeConverter
        fun fromDateTime(dateTime: DateTime) = dateTime.millis

        @TypeConverter
        fun toDateTime(millis: Long) = DateTime(millis)

        @TypeConverter
        fun fromPriority(priority: Priority) = priority.value()

        @TypeConverter
        fun toPriority(value: Int) = Priority.get(value)
    }
}