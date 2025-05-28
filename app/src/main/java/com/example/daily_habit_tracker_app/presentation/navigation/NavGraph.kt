package com.example.daily_habit_tracker_app.presentation.navigation

import android.R.attr.type
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.daily_habit_tracker_app.presentation.add_edit_habit.AddEditHabitScreen
import com.example.daily_habit_tracker_app.presentation.add_edit_habit.AddEditHabitViewModel
import com.example.daily_habit_tracker_app.presentation.auth.LoginScreen
import com.example.daily_habit_tracker_app.presentation.auth.SignupScreen
import com.example.daily_habit_tracker_app.presentation.habit_list.HabitListScreen
import com.example.daily_habit_tracker_app.presentation.habit_list.HabitListViewModel
import com.example.daily_habit_tracker_app.presentation.home.HomeScreen

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


@Composable
fun AppNavGraph(startDestination: String = "login") {
    val navController = rememberNavController()

    NavHost(navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                onNavigateToHome = { navController.navigate("home") { popUpTo("login") { inclusive = true } } },
                onNavigateToSignup = { navController.navigate("signup") }
            )
        }
        composable("signup") {
            SignupScreen(
                onNavigateToHome = { navController.navigate("home") { popUpTo("signup") { inclusive = true } } },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }
        composable("home") {
            HomeScreen(onLogout = {
                navController.navigate("login") { popUpTo("home") { inclusive = true } }
            })
        }
    }
}