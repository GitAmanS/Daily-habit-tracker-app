package com.example.daily_habit_tracker_app.presentation.habit_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.daily_habit_tracker_app.domain.model.Habit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitListScreen(
    viewModel: HabitListViewModel,
    onHabitClick: (Int?) -> Unit
) {
    val habits = viewModel.habits.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Your Habits") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onHabitClick(null) }) {
                Text("+")
            }
        }
    ) { padding ->
        if (habits.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Text(
                    text = "No habits found, add one!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(habits) { habit ->
                    HabitListItem(habit = habit, onClick = { onHabitClick(habit.id) })
                }
            }
        }
    }
}

@Composable
fun HabitListItem(habit: Habit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = habit.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = habit.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (habit.isCompleted) "Completed" else "Pending",
                style = MaterialTheme.typography.bodySmall,
                color = if (habit.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }
    }
}
