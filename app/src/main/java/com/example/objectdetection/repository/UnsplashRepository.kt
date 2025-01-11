package com.example.objectdetection.repository

import android.util.Log
import com.example.objectdetection.RetrofitInstance
import com.example.objectdetection.data.Photo
import com.example.objectdetection.data.PhotoResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnsplashRepository {
    private val api = RetrofitInstance.api
    private val accessKey = RetrofitInstance.ACCESS_KEY

    suspend fun searchPhotos(query: String): List<Photo> {
        var result = emptyList<Photo>()
        api.searchPhotos(
            authorization = "Client-ID $accessKey",
            query = query
        ).enqueue(object : Callback<PhotoResponseItem> {
            override fun onResponse(
                call: Call<PhotoResponseItem>,
                response: Response<PhotoResponseItem>
            ) {
                if (response.isSuccessful) {
                    result = response.body()?.results ?: emptyList()
                } else {
                    Log.e("Unsplash", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<PhotoResponseItem>, t: Throwable) {
                Log.e("Unsplash", "Failure: ${t.message}")
            }
        })
        return result
    }
}