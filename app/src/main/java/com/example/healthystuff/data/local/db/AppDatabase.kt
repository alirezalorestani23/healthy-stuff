package com.example.healthystuff.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.healthystuff.data.local.entity.MealEntity
import com.example.healthystuff.data.local.entity.WorkoutEntity

@Database(
    entities = [MealEntity::class, WorkoutEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun workoutDao(): WorkoutDao
}
