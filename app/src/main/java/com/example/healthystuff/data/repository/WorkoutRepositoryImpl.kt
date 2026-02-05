package com.example.healthystuff.data.repository

import com.example.healthystuff.data.local.db.WorkoutDao
import com.example.healthystuff.domain.repository.WorkoutRepository
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao
) : WorkoutRepository
