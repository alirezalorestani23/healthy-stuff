package com.example.healthystuff.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.healthystuff.data.local.entity.FoodEntryEntity
import com.example.healthystuff.data.local.entity.FoodItemEntity
import com.example.healthystuff.data.local.entity.MealEntity
import com.example.healthystuff.data.local.entity.WorkoutEntity

@Database(
    entities = [
        MealEntity::class,
        WorkoutEntity::class,
        FoodEntryEntity::class,
        FoodItemEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun foodLogDao(): FoodLogDao
}
