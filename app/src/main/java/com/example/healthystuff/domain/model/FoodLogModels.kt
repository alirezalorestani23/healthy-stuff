package com.example.healthystuff.domain.model


data class FoodEntry(
    val id: Long,
    val timestampMillis: Long,
    val title: String,
    val source: FoodEntrySource,
    val aiNotes: String?,
    val totalCalories: Int,
    val totalProteinG: Int,
    val totalCarbsG: Int,
    val totalFatG: Int,
    val items: List<FoodItem>
)

data class FoodItem(
    val id: Long,
    val name: String,
    val quantity: Double?,
    val unit: String?,
    val calories: Int,
    val proteinG: Int,
    val carbsG: Int,
    val fatG: Int,
    val confidence: Double?
)

enum class FoodEntrySource {
    MANUAL,
    AI_TEXT,
    AI_IMAGE,
    AI_TEXT_IMAGE
}

data class FoodDaySummary(
    val totalCalories: Int,
    val totalProteinG: Int,
    val totalCarbsG: Int,
    val totalFatG: Int
)