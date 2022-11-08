package com.example.workinghourscounter

data class TimeUI(
    val id: Int,
    val startMinutes: Int = 0,
    val startHours: Int = 0,
    val endMinutes: Int = 0,
    val endHours: Int = 0,
    val totalMinutes: Int = 0,
    val totalHours: Int = 0,
    val earning: Double
)