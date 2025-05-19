package com.example.daily_habit_tracker_app.domain.model

data class Habit(
    val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
)