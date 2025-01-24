package com.example.objectdetection.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.objectdetection.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    companion object {
        const val PHOTO_URL = "photoUrl"
    }

    private var photoUrl: String? = null
    private lateinit var binding: FragmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailBinding.inflate(layoutInflater)
        arguments?.let {
            photoUrl = it.getString(PHOTO_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        photoUrl?.let {
            Glide.with(binding.root.context)
                .load(photoUrl)
                .into(binding.ivDetail)
        }
        return binding.root
    }

}