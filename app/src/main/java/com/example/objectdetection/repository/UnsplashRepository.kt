package com.example.objectdetection.repository

import com.example.objectdetection.BuildConfig
import com.example.objectdetection.RetrofitInstance
import com.example.objectdetection.data.Photo

class UnsplashRepository {
    private val api = RetrofitInstance.api
    private val accessKey = BuildConfig.UNSPLASH_ACCESS_KEY

    suspend fun searchPhotos(query: String): List<Photo> {
        val response = api.searchPhotos(
            authorization = "Client-ID $accessKey",
            query = query
        )
        return response.results
    }
}