package com.example.objectdetection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.objectdetection.data.Photo
import com.example.objectdetection.repository.UnsplashRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val unsplashRepository = UnsplashRepository()

    private val _photos = MutableLiveData<List<Photo>?>()
    val photos: LiveData<List<Photo>?> = _photos

    fun searchPhotos(query: String) {
        viewModelScope.launch {
            val result = unsplashRepository.searchPhotos(query)
            _photos.value = result
        }
    }
}