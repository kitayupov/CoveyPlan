package com.kamtayupov.koviplan.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Months
import org.joda.time.Weeks
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        DateTime(parcel.readLong()),
        Priority.get(parcel.readInt()),
        parcel.readByte() != 0.toByte()
    )

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeByte(if (done) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        val DEFAULT_DATE_TIME = DateTime(-1)

        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}