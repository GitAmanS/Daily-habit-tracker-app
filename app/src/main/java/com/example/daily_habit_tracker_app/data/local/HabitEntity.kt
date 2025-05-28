package com.example.daily_habit_tracker_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val frequency: String,
    val completionHistory: String // Stored as JSON string
)
