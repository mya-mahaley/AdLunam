package com.example.adlunam.ui.images
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


import com.example.adlunam.R
import com.example.adlunam.api.NasaImage
import com.example.adlunam.databinding.ImageLayoutBinding
import com.example.adlunam.glide.Glide


class ImageAdapter(private val viewModel: ImagesViewModel)
    : ListAdapter<NasaImage, ImageAdapter.VH>(NasaImageDiff()) {
    companion object {
        const val titleKey = "titleKey"
        const val descriptionKey = "descriptionKey"
        const val imageURLKey = "imageURLKey"
    }
    class NasaImageDiff : DiffUtil.ItemCallback<NasaImage>() {
        override fun areItemsTheSame(oldItem: NasaImage, newItem: NasaImage): Boolean {
            return oldItem.nasaID == newItem.nasaID
        }
        override fun areContentsTheSame(oldItem: NasaImage, newItem: NasaImage): Boolean {
            return oldItem.title == newItem.title && oldItem.description == newItem.description &&
                    oldItem.url == newItem.url
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
        //holder.imageBinding.spaceImage.setImageResource(R.drawable.ic_launcher_background)

        val binding = holder.imageBinding
        val imageData = currentList[position]

        binding.title.text = imageData.title

        if(image.url != ""){
            Glide.glideFetch(
                image.url,
                image.url,
                holder.imageBinding.spaceImage)
        }
    }
}