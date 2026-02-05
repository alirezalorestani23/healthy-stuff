package com.example.healthystuff.app.di

import android.content.Context
import androidx.room.Room
import com.example.healthystuff.data.local.db.AppDatabase
import com.example.healthystuff.data.local.db.MealDao
import com.example.healthystuff.data.local.db.WorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "healthystuff.db"
        ).build()
    }

    @Provides
    fun provideMealDao(db: AppDatabase): MealDao = db.mealDao()

    @Provides
    fun provideWorkoutDao(db: AppDatabase): WorkoutDao = db.workoutDao()
}
