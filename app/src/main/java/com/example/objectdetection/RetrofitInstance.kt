package com.example.objectdetection

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitInstance {
    private const val BASE_URL = "https://api.unsplash.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val api: UnsplashApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(UnsplashApiService::class.java)
}
