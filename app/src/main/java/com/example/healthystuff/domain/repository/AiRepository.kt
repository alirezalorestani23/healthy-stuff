package com.example.healthystuff.domain.repository

import com.example.healthystuff.domain.model.MealEstimate

interface AiRepository{
    suspend fun estimateMealFromText(text: String): MealEstimate
}
