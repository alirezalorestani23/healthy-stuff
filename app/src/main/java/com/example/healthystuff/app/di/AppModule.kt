package com.example.healthystuff.app.di

import com.example.healthystuff.data.repository.AiRepositoryImpl
import com.example.healthystuff.data.repository.MealRepositoryImpl
import com.example.healthystuff.data.repository.WorkoutRepositoryImpl
import com.example.healthystuff.domain.repository.AiRepository
import com.example.healthystuff.domain.repository.MealRepository
import com.example.healthystuff.domain.repository.WorkoutRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindMealRepository(impl: MealRepositoryImpl): MealRepository

    @Binds
    abstract fun bindWorkoutRepository(impl: WorkoutRepositoryImpl): WorkoutRepository

    @Binds
    abstract fun bindAiRepository(impl: AiRepositoryImpl): AiRepository
}
