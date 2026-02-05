package com.example.healthystuff.domain.model

data class Workout(
    val id: Long,
    val name: String,
    val minutes: Int,
    val caloriesBurned: Int
)
