package com.example.objectdetection.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.objectdetection.databinding.ActivityDetailBinding

class DetailActivity : ComponentActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터 수신 및 UI 업데이트
//        val imageName = intent.getStringExtra("image_name")
//        binding.detailText.text = imageName
    }
}