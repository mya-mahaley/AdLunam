package com.example.adlunam.ui.images
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


import com.example.adlunam.R
import com.example.adlunam.databinding.ImageLayoutBinding
import com.example.adlunam.glide.Glide


class ImageAdapter(private val viewModel: ImagesViewModel)
    : ListAdapter<ImageData, ImageAdapter.VH>(ImageDiff()) {
    companion object {
        const val titleKey = "titleKey"
        const val descriptionKey = "descriptionKey"
        const val imageURLKey = "imageURLKey"
    }
    class ImageDiff : DiffUtil.ItemCallback<ImageData>() {
        override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
            return oldItem.title == newItem.title
        }
        override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
            return oldItem.title == newItem.title && oldItem.description == newItem.description &&
                    oldItem.imageURL == newItem.imageURL

        }
    }


    inner class VH(val imageBinding: ImageLayoutBinding)
        : RecyclerView.ViewHolder(imageBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val imageBinding = ImageLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        val holder = VH(imageBinding)
        imageBinding.root.setOnClickListener{
            ImagesViewModel.doOneImage(parent.context, currentList[holder.adapterPosition])
        }

        return holder
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val image = getItem(position)
        holder.imageBinding.spaceImage.setImageResource(R.drawable.ic_launcher_background)

        val binding = holder.imageBinding
        val imageData = currentList[position]

        binding.title.text = imageData.title

        if(image.imageURL != ""){
            Glide.glideFetch(
                image.imageURL,
                image.imageURL,
                holder.imageBinding.spaceImage)
        }
    }
}