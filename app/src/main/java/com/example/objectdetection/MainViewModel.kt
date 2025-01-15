package com.example.objectdetection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.objectdetection.data.Photo
import com.example.objectdetection.repository.UnsplashRepository

class MainViewModel : ViewModel() {
    private val unsplashRepository = UnsplashRepository()

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos

    suspend fun searchPhotos(query: String) {
        unsplashRepository.searchPhotos(query)
    }

}