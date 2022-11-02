package com.example.workinghourscounter.viewModel

import com.example.workinghourscounter.TimeUI

data class TimeState(val timeList: List<TimeUI> = emptyList(), val errorMessage: String = "")
