package com.example.healthystuff.app.di

import com.example.healthystuff.data.remote.api.AiApi
import com.example.healthystuff.data.remote.openai.OpenAiApi
import com.example.healthystuff.data.remote.openai.OpenAiAuthInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://example.com/"
    private const val OPENAI_BASE_URL = "https://api.openai.com/"


    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OpenAi

    @Provides
    @Singleton
    @OpenAi
    fun provideOpenAiOkHttpClient(base: OkHttpClient): OkHttpClient {
        return base.newBuilder()
            .addInterceptor(OpenAiAuthInterceptor())
            .build()
    }

    @Provides
    @Singleton
    @OpenAi
    fun provideOpenAiRetrofit(@OpenAi client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(OPENAI_BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenAiApi(@OpenAi retrofit: Retrofit): OpenAiApi =
        retrofit.create(OpenAiApi::class.java)


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideAiApi(retrofit: Retrofit): AiApi = retrofit.create(AiApi::class.java)
}
