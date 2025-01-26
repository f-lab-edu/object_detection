package com.example.objectdetection.fragment

import android.os.Bundle
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
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photoUrl = it.getString(PHOTO_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoUrl?.let {
            Glide.with(binding.root.context)
                .load(photoUrl)
                .into(binding.ivDetail)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}