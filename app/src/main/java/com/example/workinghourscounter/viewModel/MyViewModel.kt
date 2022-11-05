package com.example.workinghourscounter.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.workinghourscounter.TimeUI
import com.example.workinghourscounter.date.Time
import com.example.workinghourscounter.date.TimeDatabase
import com.example.workinghourscounter.date.TimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class MyViewModel(application: Application) : AndroidViewModel(application) {
    private var lastTime = Time()

    private val repository: TimeRepository

    private var count = 0

    private val _state = MutableStateFlow(TimeState())
    val state: StateFlow<TimeState> = _state.asStateFlow()

    private var rate: Double? = null

    init {
        val timeDao = TimeDatabase.getDataBase(application).timeDao()
        repository = TimeRepository(timeDao)
        collectTime()
    }


    private fun collectTime() {
        viewModelScope.launch {
            repository.readAllData().collect { timeList ->
                count = timeList.size
                updateTimeList(timeList)
            }
        }
    }

    private fun updateTimeList(timeList: List<Time>) {
        _state.update {
            it.copy(timeList = timeList.map { timeObj ->
                with(timeObj) {
                    TimeUI(
                        id = id,
                        startTime = startMinutes.convertToTime(),
                        earning = BigDecimal(earning).setScale(2,RoundingMode.HALF_EVEN).toString(),
                        totalTime = totalMinutes.convertToTime(),
                        endTime = endMinutes.convertToTime()
                    )
                }
            })

        }
    }

    private  fun Int.convertToTime():String {
        val hours = (this/60).toString().padStartZero()
        val minutes = (this%60).toString().padStartZero()
        return "$hours:$minutes"
    }
    
    private fun String.padStartZero() = padStart(2, '0')

    fun setStartTime(hour: Int, minutes: Int) {

        lastTime = lastTime.copy(startMinutes = minutes + hour * 60)
    }

    fun setEndTime(hour: Int, minutes: Int) {
        lastTime = lastTime.copy(endMinutes = minutes + hour * 60)
    }


    fun setRate(rate: String) {
        val tempRate = rate.toDoubleOrNull()
        tempRate?.let {
            if (it>0) this.rate = it
        }

    }


    fun saveTime() {
        when {
            lastTime.isFilled() && isRateFilled() -> {
                calculateEarning(rate!!)
                addTime()
                hideError()
            }
            else -> showError()
        }
    }

    private fun isRateFilled() = rate != null

    private fun calculateEarning(rate: Double) {
        lastTime = lastTime.copy(earning = rate * lastTime.totalMinutes/60)
    }

    private fun addTime() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTime(lastTime.copy(id = count))
            clearTime()
        }
    }

    private fun clearTime() {
        lastTime = Time()
    }

    fun deleteTime(time: TimeUI) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTime(Time(id = time.id))
        }
    }

    private fun showError() {
        _state.update {
            it.copy(errorMessage = "Enter the missing data")
        }
    }

    private fun hideError() {
        _state.update {
            it.copy(errorMessage = "")
        }
    }

}
