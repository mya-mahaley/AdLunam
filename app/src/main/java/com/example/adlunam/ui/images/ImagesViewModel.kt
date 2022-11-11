package com.example.adlunam.ui.images

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adlunam.OneImage


class ImagesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is images Fragment"
    }

    val text: LiveData<String> = _text

    // Put images in this list
    //val images: MutableLiveData<ImageData> = MutableLiveData()
    val images: MutableList<ImageData> = mutableListOf()

    init{
        images.add(ImageData("Moon - North Polar Mosaic, Color",
            "description",
            "https://images-assets.nasa.gov/image/PIA00404/PIA00404~thumb.jpg"))
        images.add(ImageData("GRAIL Gravity Tour of the Moon",
            "description",
            "https://images-assets.nasa.gov/image/PIA16622/PIA16622~thumb.jpg"))
        images.add(ImageData("The Moon as seen by MESSENGER",
            "description",
            "https://images-assets.nasa.gov/image/PIA14114/PIA14114~thumb.jpg"))
    }

    // Observe searchTerm livedata
    //fun observeSearchTerm(): LiveData<String> { return searchTerm }


    // Observe netPosts livedata
    //fun getImages(): LiveData<List<ImageData>> { return images }

    // Observe searchPosts livedata
    //fun observeSearchPosts(): LiveData<List<ImageData>> { return searchPosts }

    // Convenient place to put it as it is shared
    companion object {
        fun doOneImage(context: Context, imageData: ImageData) {
            var intent = Intent(context, OneImage::class.java).apply{
                putExtra(ImageAdapter.titleKey, imageData.title)
                putExtra(ImageAdapter.descriptionKey, imageData.description)
                putExtra(ImageAdapter.imageURLKey, imageData.imageURL)
            }
            context.startActivity(intent)
        }
    }


}