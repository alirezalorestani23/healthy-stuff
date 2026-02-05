package com.example.healthystuff.data.repository

import com.example.healthystuff.data.remote.api.AiApi
import com.example.healthystuff.domain.repository.AiRepository
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val api: AiApi
) : AiRepository
