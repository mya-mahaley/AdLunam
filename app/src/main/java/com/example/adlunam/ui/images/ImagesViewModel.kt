package com.example.adlunam.ui.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImagesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is images Fragment"
    }

    val text: LiveData<String> = _text

    // Put images in this list
    val images: MutableList<ImageData> = mutableListOf()

    init{
        images.add(ImageData("Space Image 1",
            "https://apod.nasa.gov/apod/image/2102/NGC_253_Hubble_960.jpg"))
    }
}