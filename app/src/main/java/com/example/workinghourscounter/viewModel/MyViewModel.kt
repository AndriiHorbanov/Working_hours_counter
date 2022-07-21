package com.example.workinghourscounter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    private val _hourStart = MutableLiveData<Int>()
    val hourStart: LiveData<Int> = _hourStart

    private val _minutesStart = MutableLiveData<Int>()
    val minutesStart: LiveData<Int> = _minutesStart

    private val _hourEnd = MutableLiveData<Int>()
    val hourEnd: LiveData<Int> = _hourEnd

    private val _minutesEnd = MutableLiveData<Int>()
    val minutesEnd: LiveData<Int> = _minutesEnd

    fun setTime(hour:Int, minutes:Int, flag:Boolean){

            if(flag){
            _hourStart.value = hour
            _minutesStart.value = minutes
        }else{
            _hourEnd.value = hour
            _minutesEnd.value = minutes
        }
    }







}