package com.example.workinghourscounter.viewModel

import com.example.workinghourscounter.TimeUI

data class TimeState(
    val timeList: List<TimeUI> = emptyList(),
    val errorMessage: String = "",
    var totalEarning: Double = 0.0,
    var totalHours: Int = 0,
    var totalMinutes: Int = 0
)
