package com.example.daily_habit_tracker_app.presentation.habit_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daily_habit_tracker_app.domain.model.Habit
import com.example.daily_habit_tracker_app.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn


@HiltViewModel
class HabitListViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits

    init {
        repository.getHabits()
            .onEach { habitsList ->
                _habits.value = habitsList
            }
            .launchIn(viewModelScope)
    }
}