package com.example.daily_habit_tracker_app.domain.repository

import com.example.daily_habit_tracker_app.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getHabits(): Flow<List<Habit>>
    suspend fun getHabitById(id: Int): Habit?
    suspend fun insertHabit(habit: Habit)
    suspend fun updateHabit(habit: Habit)
    suspend fun deleteHabit(habit: Habit)
}