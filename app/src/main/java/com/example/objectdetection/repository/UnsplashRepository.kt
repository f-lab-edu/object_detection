package com.example.objectdetection.repository

import com.example.objectdetection.BuildConfig
import com.example.objectdetection.UnsplashApiService
import com.example.objectdetection.data.Photo
import javax.inject.Inject

class UnsplashRepository @Inject constructor(
    private val api: UnsplashApiService
) {
    private val accessKey = BuildConfig.UNSPLASH_ACCESS_KEY

    suspend fun searchPhotos(query: String): List<Photo> {
        val response = api.searchPhotos(
            authorization = "Client-ID $accessKey",
            query = query
        )
        return response.results
    }
}