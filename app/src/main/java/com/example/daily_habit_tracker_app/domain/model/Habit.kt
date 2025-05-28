package com.example.daily_habit_tracker_app.domain.model

import java.time.LocalDate

data class Habit(
    val id: String = "",
    val title: String = "",
    val description: String? = null,
    val frequency: HabitFrequency = HabitFrequency.DAILY,
    val completionHistory: Map<String, Boolean> = emptyMap() // Use String keys instead of LocalDate
)

enum class HabitFrequency {
    DAILY,
    WEEKLY,
    MONTHLY
}
