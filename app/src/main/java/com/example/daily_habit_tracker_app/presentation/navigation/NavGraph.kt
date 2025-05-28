package com.example.daily_habit_tracker_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.daily_habit_tracker_app.presentation.auth.LoginScreen
import com.example.daily_habit_tracker_app.presentation.auth.SignupScreen
import com.example.daily_habit_tracker_app.presentation.home.HomeScreen



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