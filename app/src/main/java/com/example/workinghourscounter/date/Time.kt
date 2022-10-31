package com.example.workinghourscounter.date

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workinghourscounter.date.TimeDatabase.Companion.DATABASE_NAME

@Entity(tableName = DATABASE_NAME)
data class Time(
    @PrimaryKey(autoGenerate = false)
    val id: Int = DEFAULT_VALUE,
    val startHours: Int = DEFAULT_VALUE,
    val startMinutes: Int = DEFAULT_VALUE,
    val endHours: Int = DEFAULT_VALUE,
    val endMinutes: Int = DEFAULT_VALUE
) {

    fun isFilled() = startHours != DEFAULT_VALUE
            && startMinutes != DEFAULT_VALUE
            && endHours != DEFAULT_VALUE
            && endMinutes != DEFAULT_VALUE

    private companion object {
        const val DEFAULT_VALUE = -1
    }
}