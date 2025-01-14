package com.example.objectdetection.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.objectdetection.BuildConfig
import com.example.objectdetection.ImageListAdapter
import com.example.objectdetection.MainViewModel
import com.example.objectdetection.RetrofitInstance
import com.example.objectdetection.data.PhotoResponseItem
import com.example.objectdetection.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ImageListAdapter

    private val linearLayoutManager = LinearLayoutManager(this)
    private val gridLayoutManager = GridLayoutManager(this, 2)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSearchView()

        //TODO 현재는 이미지 잘 들어오는지 확인하기 위한 고정 값
        val query = "nature"
        searchPhotos(query)

        adapter = ImageListAdapter(emptyList()) { selectedImage ->
            val intent = Intent(this, DetailActivity::class.java)
//            intent.putExtra("image_name", selectedImage)
            startActivity(intent)
        }
        binding.rvImage.adapter = adapter
        binding.rvImage.layoutManager = linearLayoutManager
        binding.ivList.setOnClickListener {
            binding.ivList.isSelected = !binding.ivList.isSelected

            binding.rvImage.layoutManager = if (binding.ivList.isSelected) {
                gridLayoutManager
            } else {
                linearLayoutManager
            }
        }

//        viewModel.photos.observe(this, Observer { photos ->
//            Toast.makeText(this@MainActivity, "result",Toast.LENGTH_LONG).show()
//            adapter.imageList = photos
//            adapter.notifyDataSetChanged()
//        })
    }

    private fun initSearchView() {
        binding.sv.isSubmitButtonEnabled = true
        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let {
//                    CoroutineScope(Dispatchers.IO).launch {
//                        viewModel.searchPhotos(query)
//                    }
//                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    //TODO mvvm과 코루틴 적용필요
    private fun searchPhotos(query: String) {
        RetrofitInstance.api.searchPhotos(
            authorization = "Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}",
            query = query
        ).enqueue(object : Callback<PhotoResponseItem> {
            override fun onResponse(
                call: Call<PhotoResponseItem>,
                response: Response<PhotoResponseItem>
            ) {
                if (response.isSuccessful) {
                    val photos = response.body()?.results
                    photos?.let {
                        adapter.updateData(photos)
                    }
                    photos?.forEach { photo ->
                        Log.d("Unsplash", "Photo: ${photo.urls.small}, User: ${photo.user.name}")
                    }
                } else {
                    Log.e("Unsplash", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<PhotoResponseItem>, t: Throwable) {
                Log.e("Unsplash", "Failure: ${t.message}")
            }
        })
    }
}
