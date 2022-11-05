package com.example.workinghourscounter.date

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workinghourscounter.date.TimeDatabase.Companion.DATABASE_NAME

@Entity(tableName = DATABASE_NAME)
data class Time(
    @PrimaryKey(autoGenerate = false)
    val id: Int = DEFAULT_VALUE,
    val startMinutes: Int = DEFAULT_VALUE,
    val endMinutes: Int = DEFAULT_VALUE,
    val earning: Double = DEFAULT_VALUE_DOUBLE
) {

    val totalMinutes get() = endMinutes-startMinutes

    fun isFilled() = startMinutes != DEFAULT_VALUE
            && endMinutes != DEFAULT_VALUE


    private companion object {
        const val DEFAULT_VALUE = -1
        const val DEFAULT_VALUE_DOUBLE = -1.0
    }
}