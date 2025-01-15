package com.example.objectdetection.activity

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.objectdetection.ImageListAdapter
import com.example.objectdetection.MainViewModel
import com.example.objectdetection.R
import com.example.objectdetection.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ImageListAdapter

    private val linearLayoutManager = LinearLayoutManager(this)
    private val gridLayoutManager = GridLayoutManager(this, 2)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val apiErrorMessage = getString(R.string.api_error)

        setContentView(binding.root)
        initSearchView()

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

        viewModel.photos.observe(this, Observer { photos ->
            photos?.let {
                adapter.updateData(photos)
            } ?: run {
                Toast.makeText(this@MainActivity, apiErrorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initSearchView() {
        binding.sv.isSubmitButtonEnabled = true
        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchPhotos(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }
}
