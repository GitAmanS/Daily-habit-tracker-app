package com.example.daily_habit_tracker_app.presentation.add_edit_habit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daily_habit_tracker_app.domain.model.Habit
import com.example.daily_habit_tracker_app.domain.repository.HabitRepository
import com.example.daily_habit_tracker_app.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditHabitViewModel @Inject constructor(
    private val repository: HabitRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private var habitId: Int? = null

    val currentHabitId: Int?
        get() = habitId

    init {
        savedStateHandle.get<Int>(Screen.AddEditHabit.HABIT_ID)?.let { id ->
            if (id != -1) {
                habitId = id
                viewModelScope.launch {
                    repository.getHabitById(id)?.let { habit ->
                        _title.value = habit.title
                        _description.value = habit.description
                    }
                }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
    }

    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }

    fun saveHabit(onSaved: () -> Unit) {
        val currentTitle = _title.value.trim()
        val currentDescription = _description.value.trim()

        if (currentTitle.isEmpty()) return

        viewModelScope.launch {
            val habit = Habit(
                id = habitId ?: 0,
                title = currentTitle,
                description = currentDescription,
                isCompleted = false
            )
            if (habitId == null) {
                repository.insertHabit(habit)
            } else {
                repository.updateHabit(habit)
            }
            onSaved()
        }
    }
}