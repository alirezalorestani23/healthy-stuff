package com.example.healthystuff.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthystuff.presentation.screen.dashboard.DashboardScreen
import com.example.healthystuff.presentation.screen.food.FoodLogScreen
import com.example.healthystuff.presentation.screen.profile.ProfileScreen
import com.example.healthystuff.presentation.screen.workout.WorkoutLogScreen

@Composable
fun NavGraph(startDestination: String = Screen.Dashboard.route) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Dashboard.route) { DashboardScreen() }
        composable(Screen.FoodLog.route) { FoodLogScreen() }
        composable(Screen.WorkoutLog.route) { WorkoutLogScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}
