package com.example.objectdetection

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.objectdetection.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSearchView()

        //TODO 리스트 형태를 보기 위한 임의의 쓰레기 값 변경 필요
        val list = ArrayList<String>()
        list.add("A")
        list.add("A")
        list.add("A")
        list.add("A")
        list.add("A")
        list.add("A")
        list.add("A")
        list.add("A")
        list.add("A")
        list.add("A")
        list.add("A")
        list.add("A")
        list.add("A")
        list.add("A")

        val adapter = ImageListAdapter(list) { selectedImage ->
            val intent = Intent(this, DetailActivity::class.java)
//            intent.putExtra("image_name", selectedImage)
            startActivity(intent)
        }
        binding.rvImage.adapter = adapter
        binding.rvImage.layoutManager = LinearLayoutManager(this)
        binding.ivList.setOnClickListener {
            binding.ivList.isSelected = !binding.ivList.isSelected
            if (binding.ivList.isSelected) {
                // grid layout
                binding.rvImage.layoutManager = GridLayoutManager(this, 2)
            } else {
                binding.rvImage.layoutManager = LinearLayoutManager(this)
            }
        }
    }

    private fun initSearchView() {
        binding.sv.isSubmitButtonEnabled = true
        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }
}
