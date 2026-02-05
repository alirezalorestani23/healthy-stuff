package com.example.healthystuff.data.repository

import com.example.healthystuff.data.local.db.MealDao
import com.example.healthystuff.domain.repository.MealRepository
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealDao: MealDao
) : MealRepository
