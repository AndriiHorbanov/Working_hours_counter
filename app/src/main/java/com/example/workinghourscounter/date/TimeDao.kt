package com.example.workinghourscounter.date

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TimeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTime(time:Time)

    @Query("SELECT * FROM time_table ORDER BY id ASC")
    fun readAll():LiveData<List<Time>>
}