package com.example.objectdetection.repository

import android.util.Log
import com.example.objectdetection.BuildConfig
import com.example.objectdetection.RetrofitInstance
import com.example.objectdetection.data.Photo

class UnsplashRepository {
    private val api = RetrofitInstance.api
    private val accessKey = BuildConfig.UNSPLASH_ACCESS_KEY

    suspend fun searchPhotos(query: String): List<Photo> {
        return try {
            val response = api.searchPhotos(
                authorization = "Client-ID $accessKey",
                query = query
            )
            response.results
        } catch (e: Exception) {
            Log.e("Unsplash", "Failure: ${e.message}")
            emptyList()
        }
    }
}