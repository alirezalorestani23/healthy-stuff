package com.example.healthystuff.data.remote.openai

import okhttp3.Interceptor
import okhttp3.Response
import com.example.healthystuff.BuildConfig


class OpenAiAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = BuildConfig.OPENAI_API_KEY

        check(apiKey.isNotBlank()) { "OPENAI_API_KEY must be set" }

        val request = chain.request().newBuilder().header("Authorization", "Bearer $apiKey").build()
        return chain.proceed(request)
    }
}



