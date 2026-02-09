package com.example.healthystuff.data.repository

import com.example.healthystuff.data.remote.openai.OpenAiApi
import com.example.healthystuff.data.remote.openai.dto.ChatCompletionRequest
import com.example.healthystuff.data.remote.openai.dto.ChatMessage
import com.example.healthystuff.data.remote.openai.dto.ResponseFormat
import com.example.healthystuff.domain.model.MealEstimate
import com.example.healthystuff.domain.repository.AiRepository
import com.squareup.moshi.Moshi
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val openAiApi: OpenAiApi,
    private val moshi: Moshi
) : AiRepository {
    override suspend fun estimateMealFromText(text: String): MealEstimate {
        val cleanedText = text.trim()
        require(cleanedText.isNotBlank()) { "Please describe your meal." }
        val request = ChatCompletionRequest(
            model = "gpt-4o-mini",
            temperature = 0.2,
            response_format = ResponseFormat(type = "json_object"),
            messages = listOf(
                ChatMessage(
                    role = "system",
                    content =
                        "You are a nutrition assistant. " +
                                "Return ONLY valid JSON. No markdown, no extra text."
                ),
                ChatMessage(
                    role = "user",
                    content =
                        "Estimate calories and macros from this meal description.\n\n" +
                                "Return JSON with this shape:\n" +
                                "{\n" +
                                "  \"title\": string,\n" +
                                "  \"items\": [\n" +
                                "    {\"name\": string, \"calories\": int, \"proteinG\": int, \"carbsG\": int, \"fatG\": int, \"confidence\": number |null }\n" +
                                "  ],\n" +
                                "  \"notes\": [string],\n" +
                                "  \"warnings\": [string]\n" +
                                "}\n\n" +
                                "Meal: " + cleanedText
                )
            )
        )

        val response = openAiApi.createChatCompletion(request)
        val json =
            response.choices.firstOrNull()?.message?.content
                ?: error("Empty Response from OpenAI")

        val adapter = moshi.adapter(MealEstimate::class.java)
        return requireNotNull(adapter.fromJson(json)) {
            "Failed to parse MealEstimate from OpenAI JSON: $json"
        }

    }
}
