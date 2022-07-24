package com.example.workinghourscounter.date

import androidx.lifecycle.LiveData

class TimeRepository(private val timeDao: TimeDao) {
    val readAllData: LiveData<List<Time>> = timeDao.readAll()

    suspend fun addTime(time: Time){
        timeDao.addTime(time)
    }
}