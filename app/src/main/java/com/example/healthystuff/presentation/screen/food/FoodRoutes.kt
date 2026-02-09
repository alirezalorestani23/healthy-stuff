package com.example.healthystuff.presentation.screen.food


object FoodRoutes {
    const val Detail = "food/detail/{entryId}"
    fun detail(entryId: Long) = "food/detail/$entryId"
}