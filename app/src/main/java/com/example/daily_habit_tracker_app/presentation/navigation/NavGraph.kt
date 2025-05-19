package com.example.daily_habit_tracker_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.daily_habit_tracker_app.presentation.add_edit_habit.AddEditHabitScreen
import com.example.daily_habit_tracker_app.presentation.add_edit_habit.AddEditHabitViewModel
import com.example.daily_habit_tracker_app.presentation.habit_list.HabitListScreen
import com.example.daily_habit_tracker_app.presentation.habit_list.HabitListViewModel

sealed class Screen(val route: String) {
    object HabitList : Screen("habit_list")
    object AddEditHabit : Screen("add_edit_habit") {
        const val HABIT_ID = "habitId"
        fun createRoute(habitId: Int? = null) =
            if (habitId != null) "add_edit_habit/$habitId" else "add_edit_habit"
    }
}

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.HabitList.route) {

        composable(route = Screen.HabitList.route) {
            val habitListViewModel: HabitListViewModel = hiltViewModel()
            HabitListScreen(
                viewModel = habitListViewModel,
                onHabitClick = { habitId ->
                    navController.navigate(Screen.AddEditHabit.createRoute(habitId))
                }
            )
        }

        composable(
            route = "${Screen.AddEditHabit.route}/{${Screen.AddEditHabit.HABIT_ID}}",
            arguments = listOf(
                navArgument(Screen.AddEditHabit.HABIT_ID) {
                    type = androidx.navigation.NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getInt(Screen.AddEditHabit.HABIT_ID) ?: -1
            val addEditViewModel: AddEditHabitViewModel = hiltViewModel()
            AddEditHabitScreen(
                viewModel = addEditViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.AddEditHabit.route) {
            val addEditViewModel: AddEditHabitViewModel = hiltViewModel()
            AddEditHabitScreen(
                viewModel = addEditViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}