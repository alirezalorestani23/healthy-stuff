package com.example.healthystuff.data.remote.api

import com.example.healthystuff.data.remote.dto.AiMealParseRequest
import com.example.healthystuff.data.remote.dto.AiMealParseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AiApi {
    @POST("ai/meal/parse")
    suspend fun parseMeal(@Body request: AiMealParseRequest): AiMealParseResponse
}
