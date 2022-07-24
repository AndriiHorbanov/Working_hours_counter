package com.example.workinghourscounter.date

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Time::class], version = 1, exportSchema = false)
abstract class TimeDatabase : RoomDatabase() {
    abstract fun timeDao(): TimeDao

    companion object {
        @Volatile
        private var INSTANCE: TimeDatabase? = null

        fun getDataBase(context: Context): TimeDatabase {
            val templateInstance = INSTANCE
            if (templateInstance != null) {
                return templateInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TimeDatabase::class.java,
                    "time_table"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}