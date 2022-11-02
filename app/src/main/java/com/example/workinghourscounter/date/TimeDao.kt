package com.example.workinghourscounter.date

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTime(time:Time)

    @Query("SELECT * FROM time_table ORDER BY id ASC")
    fun readAll():Flow<List<Time>>

    @Delete
    suspend fun deleteTime(time:Time)
}