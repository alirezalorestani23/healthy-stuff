package com.example.healthystuff.data.remote.openai

import com.example.healthystuff.data.remote.openai.dto.ChatCompletionRequest
import com.example.healthystuff.data.remote.openai.dto.ChatCompletionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAiApi {
    @POST("v1/chat/completions")
    suspend fun createChatCompletion(
        @Body request: ChatCompletionRequest
    ): ChatCompletionResponse
}


