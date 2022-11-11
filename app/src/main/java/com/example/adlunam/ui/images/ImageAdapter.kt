package com.example.adlunam.ui.images
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.adlunam.R
import com.example.adlunam.databinding.ImageLayoutBinding


class ImageAdapter(private val viewModel: ImagesViewModel)
    : RecyclerView.Adapter<ImageAdapter.VH>() {

    // Create new variable that is the list of images/image data
    private var list = mutableListOf<String>().apply {
        addAll(viewModel.images.shuffled())
    }

    private fun addAll(elements: List<ImageData>) {

    }

    inner class VH(val imageBinding: ImageLayoutBinding): RecyclerView.ViewHolder(imageBinding.root) {
        init {
            imageBinding.root.setOnClickListener {
                // DO SOMETHING
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val imageBinding = ImageLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(imageBinding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val image = getItem(position)
        holder.imageBinding.spaceImage.setImageResource(R.drawable.ic_launcher_background)

    }
    fun getItem(position: Int):String{
        return list[position]
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}