package com.example.workinghourscounter.date

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_table")
data class Time(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val startHours: Int,
    val startMinutes: Int,
    val endHours: Int,
    val endMinutes: Int
) {
}