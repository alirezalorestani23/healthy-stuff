package com.example.healthystuff.data.remote.openai.dto

data class ChatCompletionRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val temperature: Double? = null,
    val response_format: ResponseFormat? = null
)

data class ChatMessage(
    val role: String,
    val content: String
)
data class ResponseFormat(
    val type: String,
)

