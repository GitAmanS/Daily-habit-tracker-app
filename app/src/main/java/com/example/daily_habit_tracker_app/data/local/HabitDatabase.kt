package com.example.daily_habit_tracker_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.daily_habit_tracker_app.data.remote.Converters

@Database(entities = [HabitEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}
