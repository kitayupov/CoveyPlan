package com.kamtayupov.koviplan.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import org.joda.time.DateTime
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