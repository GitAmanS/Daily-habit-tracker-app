package com.example.daily_habit_tracker_app.data.repository

import com.example.daily_habit_tracker_app.data.local.HabitDao
import com.example.daily_habit_tracker_app.data.local.HabitEntity
import com.example.daily_habit_tracker_app.domain.model.Habit
import com.example.daily_habit_tracker_app.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val dao: HabitDao
) : HabitRepository {

    override fun getHabits(): Flow<List<Habit>> {
        return dao.getHabits().map { list ->
            list.map { it.toHabit() }
        }
    }

    override suspend fun getHabitById(id: Int): Habit? {
        return dao.getHabitById(id)?.toHabit()
    }

    override suspend fun insertHabit(habit: Habit) {
        dao.insertHabit(habit.toEntity())
    }

    override suspend fun updateHabit(habit: Habit) {
        dao.updateHabit(habit.toEntity())
    }

    override suspend fun deleteHabit(habit: Habit) {
        dao.deleteHabit(habit.toEntity())
    }
}

// Mappers
fun HabitEntity.toHabit(): Habit {
    return Habit(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted
    )
}

fun Habit.toEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted
    )
}
