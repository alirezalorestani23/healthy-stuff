package com.example.healthystuff.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.healthystuff.R

sealed class Screen(
    val route: String, @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int
) {
    data object Dashboard : Screen("dashboard", R.string.tab_dashboard, R.drawable.ic_dashboard)
    data object FoodLog : Screen("food_log", R.string.tab_food, R.drawable.ic_food)
    data object WorkoutLog : Screen("workout_log", R.string.tab_workout, R.drawable.ic_workout)
    data object Profile : Screen("profile", R.string.tab_profile, R.drawable.ic_profile)
    companion object {
        // Use a getter so callers always receive a fresh list (prevents any accidental mutation/old state).
        val bottomTabs: List<Screen>
            get() = listOf(Dashboard, FoodLog, WorkoutLog, Profile)
    }
}
