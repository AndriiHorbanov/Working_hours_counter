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

class MyViewModel(application: Application) : AndroidViewModel(application) {
    private var lastTime = Time()

    private val repository: TimeRepository

    private var count = 0

    private val _state = MutableStateFlow(TimeState())
    val state: StateFlow<TimeState> = _state.asStateFlow()

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
                TimeUI(
                    id = timeObj.id,
                    startTime = timeObj.startHours.convertToString() + DELIMITER + timeObj.startMinutes.convertToString(),
                    endTime = timeObj.endHours.convertToString() + DELIMITER + timeObj.endMinutes.convertToString()
                )
            })

        }
    }

    private fun Int.convertToString() = toString().padStart(2, '0')

    fun setStartTime(hour: Int, minutes: Int) {

        lastTime = lastTime.copy(startHours = hour, startMinutes = minutes)
    }

    fun setEndTime(hour: Int, minutes: Int) {
        lastTime = lastTime.copy(endHours = hour, endMinutes = minutes)
    }

    fun saveTime() {
        when {
            lastTime.isFilled() -> {
                addTime()
                hideError()
            }
            else -> showError()
        }
    }

    private fun addTime() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTime(lastTime.copy(id = count))
            clearTime()
        }
    }

    private fun clearTime(){
        lastTime = Time()
    }

    fun deleteTime(time : TimeUI){
        viewModelScope.launch ( Dispatchers.IO ) {
            repository.deleteTime(Time(id = time.id))
        }
    }

    private fun showError() {
        _state.update {
            it.copy(errorMessage = "Enter the missing data")
        }
    }

    private fun hideError(){
        _state.update {
            it.copy(errorMessage = "")
        }
    }

    private companion object {
        const val DELIMITER = " : "
    }


}
