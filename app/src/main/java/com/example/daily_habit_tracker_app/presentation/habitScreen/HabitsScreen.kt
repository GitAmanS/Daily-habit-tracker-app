package com.example.daily_habit_tracker_app.presentation.habitScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.daily_habit_tracker_app.domain.model.Habit

@Composable
fun HabitsScreen(viewModel: HabitViewModel = hiltViewModel()) {
    val habits by viewModel.habits.collectAsState()

    var newHabitTitle by remember { mutableStateOf("") }
    var newHabitDescription by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Your Habits",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Add Habit UI
        OutlinedTextField(
            value = newHabitTitle,
            onValueChange = { newHabitTitle = it },
            label = { Text("Habit Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = newHabitDescription,
            onValueChange = { newHabitDescription = it },
            label = { Text("Description (optional)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (newHabitTitle.isNotBlank()) {
                    viewModel.addHabit(
                        Habit(
                            title = newHabitTitle,
                            description = newHabitDescription
                        )
                    )
                    newHabitTitle = ""
                    newHabitDescription = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Habit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Habit List
        if (habits.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No habits found", textAlign = TextAlign.Center)
            }
        } else {
            LazyColumn {
                items(habits) { habit ->
                    HabitItem(
                        habit = habit,
                        onDelete = { viewModel.deleteHabit(it) },
                        onEdit = { viewModel.updateHabit(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun HabitItem(habit: Habit, onDelete: (Habit) -> Unit, onEdit: (Habit) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = habit.title, style = MaterialTheme.typography.titleMedium)
                habit.description?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyMedium)
                }
            }

            Row {
                IconButton(onClick = {
                    // For simplicity, toggling description to empty string as example of edit
                    onEdit(habit.copy(description = habit.description?.let { "Edited" } ?: "Edited"))
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Habit")
                }
                IconButton(onClick = { onDelete(habit) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Habit")
                }
            }
        }
    }
}
