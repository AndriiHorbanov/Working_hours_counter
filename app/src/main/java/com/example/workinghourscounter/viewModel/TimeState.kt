package com.example.workinghourscounter.viewModel

import com.example.workinghourscounter.DataTime

data class TimeState(val timeList: List<DataTime> = emptyList(), val errorMessage: String = "")
