package com.example.healthystuff.data.mapper

import com.example.healthystuff.data.local.entity.FoodEntryEntity
import com.example.healthystuff.data.local.entity.FoodItemEntity
import com.example.healthystuff.domain.model.FoodEntry
import com.example.healthystuff.domain.model.FoodEntrySource
import com.example.healthystuff.domain.model.FoodItem


fun FoodEntryEntity.toDomain(items: List<FoodItemEntity>): FoodEntry {
    return FoodEntry(
        id = id,
        timestampMillis = timestampMillis,
        title = title,
        source = runCatching { FoodEntrySource.valueOf(source) }.getOrElse { FoodEntrySource.MANUAL },
        aiNotes = aiNotes,
        totalCalories = totalCalories,
        totalProteinG = totalProteinG,
        totalCarbsG = totalCarbsG,
        totalFatG = totalFatG,
        items = items.map { it.toDomain() }
    )
}

fun FoodItemEntity.toDomain(): FoodItem {
    return FoodItem(
        id = id,
        name = name,
        quantity = quantity,
        unit = unit,
        calories = calories,
        proteinG = proteinG,
        carbsG = carbsG,
        fatG = fatG,
        confidence = confidence

    )
}

fun FoodEntry.toEntity(): FoodEntryEntity {
    return FoodEntryEntity(
        id = id,
        timestampMillis = timestampMillis,
        title = title,
        source = source.name,
        aiNotes = aiNotes,
        totalCalories = totalCalories,
        totalProteinG = totalProteinG,
        totalCarbsG = totalCarbsG,
        totalFatG = totalFatG
    )
}

fun FoodItem.toEntity(entryId: Long): FoodItemEntity {
    return FoodItemEntity(
        id = id,
        entryId = entryId,
        name = name,
        quantity = quantity,
        unit = unit,
        calories = calories,
        proteinG = proteinG,
        carbsG = carbsG,
        fatG = fatG,
        confidence = confidence
    )
}
