package com.example.workinghourscounter

data class TimeUI(
    val id: Int,
    val startTime: String = "",
    val endTime: String = "",
    val totalTime: String,
    val earning: String
)