package com.example.healthystuff.data.remote.openai.dto

data class ChatCompletionResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: ChatMessage
)
