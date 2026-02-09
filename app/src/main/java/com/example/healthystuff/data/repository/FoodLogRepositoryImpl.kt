package com.example.healthystuff.data.repository

import com.example.healthystuff.data.local.db.FoodLogDao
import com.example.healthystuff.data.mapper.toDomain
import com.example.healthystuff.data.mapper.toEntity
import com.example.healthystuff.domain.model.FoodDaySummary
import com.example.healthystuff.domain.model.FoodEntry
import com.example.healthystuff.domain.repository.FoodLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FoodLogRepositoryImpl @Inject constructor(private val dao: FoodLogDao) : FoodLogRepository {

    override fun observeEntriesBetween(
        startMillis: Long,
        endMillis: Long
    ): Flow<List<FoodEntry>> {
        return dao.observeEntriesBetween(startMillis, endMillis).map { entries ->
            entries.map { it.toDomain(items = emptyList()) }
        }
    }

    override fun observeDaySummaryBetween(
        startMillis: Long,
        endMillis: Long
    ): Flow<FoodDaySummary> {
        return dao.observeEntriesBetween(startMillis, endMillis).map { entities ->
            FoodDaySummary(
                totalCalories = entities.sumOf { it.totalCalories },
                totalProteinG = entities.sumOf { it.totalProteinG },
                totalCarbsG = entities.sumOf { it.totalCarbsG },
                totalFatG = entities.sumOf { it.totalFatG }
            )
        }
    }

    override suspend fun insertFoodEntry(foodEntry: FoodEntry): Long {
        val entryId = dao.insertFoodEntry(foodEntry.toEntity().copy(id = 0))
        val items = foodEntry.items.map { it.toEntity(entryId).copy(id = 0) }
        dao.insertFoodItem(items)
        return entryId
    }

    override suspend fun updateFoodEntry(foodEntry: FoodEntry) {
        dao.updateFoodEntry(foodEntry.toEntity())
        dao.deleteItemsForFoodEntry(foodEntry.id)
        val items = foodEntry.items.map { it.toEntity(foodEntry.id).copy(id = 0) }
        dao.insertFoodItem(foodItems = items)
    }

    override suspend fun deleteFoodEntry(entryId: Long) {
        dao.deleteFoodEntry(entryId)
    }


    override suspend fun getFoodEntry(entryId: Long): FoodEntry? {
        val entry = dao.getFoodEntry(entryId) ?: return null
        val items = dao.getItemsForFoodEntry(entryId)
        return entry.toDomain(items)
    }

}