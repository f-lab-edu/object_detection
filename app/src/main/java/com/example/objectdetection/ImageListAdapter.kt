package com.example.objectdetection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.objectdetection.databinding.ItemRecyclerBinding

class ImageListAdapter(
    private val imageList: ArrayList<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ImageListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListAdapter.Holder {
        val binding =
            ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: ImageListAdapter.Holder, position: Int) {
        //TODO 아이템에 대해서 정리 필요
        val item = imageList[position]
        holder.binding.root.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        //초기화 가능
//        val image = binding.image
    }
}