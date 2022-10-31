package com.example.workinghourscounter.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.workinghourscounter.DataTime
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
        readTime()
    }


    private fun readTime() {
        viewModelScope.launch {
            repository.readAllData().collect { timeList ->
                count = timeList.size
                _state.update {
                    it.copy(timeList = timeList.map { timeObj ->
                        DataTime(
                            startTime = timeObj.startHours.convertToString() + DELIMITER + timeObj.startMinutes.convertToString(),
                            endTime = timeObj.endHours.convertToString() + DELIMITER + timeObj.endMinutes.convertToString()
                        )
                    })

                }
            }
        }
    }

    fun saveTime() {
        when {
            lastTime.isFilled() -> addTime()
            else -> showError()
        }
    }

    private fun showError() {

    }

    private fun addTime() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTime(lastTime.copy(id = count))
        }
    }

    fun setStartTime(hour: Int, minutes: Int) {

        lastTime = lastTime.copy(startHours = hour, startMinutes = minutes)
    }

    fun setEndTime(hour: Int, minutes: Int) {
        lastTime = lastTime.copy(endHours = hour, endMinutes = minutes)
    }

    fun Int.convertToString() = toString().padStart(2, '0')

    private companion object {
        const val DELIMITER = " : "
    }


}
