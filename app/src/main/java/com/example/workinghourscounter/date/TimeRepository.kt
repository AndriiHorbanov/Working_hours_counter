package com.example.workinghourscounter.date

import kotlinx.coroutines.flow.Flow

class TimeRepository(private val timeDao: TimeDao) {
    fun readAllData(): Flow<List<Time>> = timeDao.readAll()

    suspend fun addTime(time: Time){
        timeDao.addTime(time)
    }
}