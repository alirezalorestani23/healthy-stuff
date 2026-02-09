package com.example.healthystuff.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "food_entries")
data class FoodEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestampMillis: Long,
    val title: String,
    val source: String,
    val aiNotes: String?,
    val totalCalories: Int,
    val totalProteinG: Int,
    val totalCarbsG: Int,
    val totalFatG: Int,


    )