package com.example.objectdetection

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.objectdetection.data.Photo
import com.example.objectdetection.repository.UnsplashRepository
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class MainViewModel : ViewModel() {
    private val unsplashRepository = UnsplashRepository()

    private val _photos = MutableLiveData<List<Photo>?>()
    val photos: LiveData<List<Photo>?> = _photos

    private val _imageSaved = MutableLiveData<Boolean>()
    val imageSaved: LiveData<Boolean> get() = _imageSaved


    fun searchPhotos(query: String) {
        viewModelScope.launch {
            val result = unsplashRepository.searchPhotos(query)
            _photos.value = result
        }
    }

    fun saveImageToGallery(context: Context, bitmap: Bitmap, photoName: String) {
        viewModelScope.launch {
            val fileName = "${photoName}_${System.currentTimeMillis()}.jpg"
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // Android 10 이상 (MediaStore 사용)
                    val contentValues = ContentValues().apply {
                        put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                        put(MediaStore.Images.Media.IS_PENDING, 1)
                    }

                    val resolver = context.contentResolver
                    val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                    imageUri?.let { uri ->
                        resolver.openOutputStream(uri)?.use { outputStream ->
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        }
                        contentValues.clear()
                        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                        resolver.update(uri, contentValues, null, null)

                        _imageSaved.value = true
                    } ?: {
                        _imageSaved.value = false
                    }
                } else {
                    // Android 9 이하
                    val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    val file = File(directory, fileName)

                    val outputStream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                    outputStream.close()

                    MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), arrayOf("image/jpeg"), null)

                    _imageSaved.value = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ObjectDetection", "image save error ${e.printStackTrace()}")
                _imageSaved.value = false
            }
        }
    }
}