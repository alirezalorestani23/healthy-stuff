package com.example.healthystuff.domain.model

import kotlin.math.roundToInt

data class MealEstimate(
    val title: String,
    val items: List<MealEstimateItem>,
    val notes: List<String> = emptyList(),
    val warnings: List<String> = emptyList()
) {
    val totals: FoodDaySummary
        get() = FoodDaySummary(
            totalCalories = items.sumOf { it.calories },
            totalProteinG = items.sumOf { it.proteinG.roundToInt() },
            totalCarbsG = items.sumOf { it.carbsG.roundToInt() },
            totalFatG = items.sumOf { it.fatG.roundToInt() }
        )
}

data class MealEstimateItem(
    val name: String,
    val calories: Int,
    val proteinG: Double = 0.0,
    val carbsG: Double = 0.0,
    val fatG: Double = 0.0,
    val confidence: Double? = null
)


