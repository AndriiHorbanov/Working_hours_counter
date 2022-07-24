package com.example.workinghourscounter.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.workinghourscounter.date.Time
import com.example.workinghourscounter.date.TimeDatabase
import com.example.workinghourscounter.date.TimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application): AndroidViewModel(application) {
    private val _hourStart = MutableLiveData<Int>()
    val hourStart: LiveData<Int> = _hourStart

    private val _minutesStart = MutableLiveData<Int>()
    val minutesStart: LiveData<Int> = _minutesStart

    private val _hourEnd = MutableLiveData<Int>()
    val hourEnd: LiveData<Int> = _hourEnd

    private val _minutesEnd = MutableLiveData<Int>()
    val minutesEnd: LiveData<Int> = _minutesEnd

    private val readAllTime: LiveData<List<Time>>
    private val repository: TimeRepository

    init {
        val timeDao = TimeDatabase.getDataBase(application).timeDao()
        repository = TimeRepository(timeDao)
        readAllTime = repository.readAllData
    }

    fun addTime(time:Time){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTime(time)
        }
    }

    fun setTime(hour:Int, minutes:Int, flag:Boolean){

            if(flag){
            _hourStart.value = hour
            _minutesStart.value = minutes
        }else{
            _hourEnd.value = hour
            _minutesEnd.value = minutes
        }
    }


     fun CheckDataToDatabase(): Boolean {
         return hourStart.value != null && minutesStart.value != null && hourEnd.value != null && minutesEnd.value != null
    }




}