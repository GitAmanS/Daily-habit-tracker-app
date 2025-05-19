package com.example.daily_habit_tracker_app.data.local

import kotlinx.coroutines.flow.Flow
import androidx.room.*

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id = :id")
    suspend fun getHabitById(id: Int): HabitEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Update
    suspend fun updateHabit(habitEntity: HabitEntity)
}