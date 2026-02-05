package com.example.healthystuff.presentation.navigation

sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object FoodLog : Screen("food_log")
    data object WorkoutLog : Screen("workout_log")
    data object Profile : Screen("profile")
}
