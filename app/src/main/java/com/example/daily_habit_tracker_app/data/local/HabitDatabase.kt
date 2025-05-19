package com.example.daily_habit_tracker_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HabitEntity::class], version = 1)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}