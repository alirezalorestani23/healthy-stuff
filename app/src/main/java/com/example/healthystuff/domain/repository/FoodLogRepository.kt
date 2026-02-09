package com.example.healthystuff.domain.repository

import com.example.healthystuff.domain.model.FoodDaySummary
import com.example.healthystuff.domain.model.FoodEntry
import kotlinx.coroutines.flow.Flow

interface FoodLogRepository {
    fun observeEntriesBetween(startMillis: Long, endMillis: Long): Flow<List<FoodEntry>>
    fun observeDaySummaryBetween(startMillis: Long, endMillis: Long): Flow<FoodDaySummary>

    suspend fun insertFoodEntry(foodEntry: FoodEntry): Long
    suspend fun updateFoodEntry(foodEntry: FoodEntry)
    suspend fun deleteFoodEntry(entryId: Long)

    suspend fun getFoodEntry(entryId: Long): FoodEntry?



}