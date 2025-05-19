package com.example.daily_habit_tracker_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.daily_habit_tracker_app.presentation.navigation.NavGraph
import com.example.daily_habit_tracker_app.ui.theme.DailyhabittrackerappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyhabittrackerappTheme {
                NavGraph()
            }
        }
    }
}
