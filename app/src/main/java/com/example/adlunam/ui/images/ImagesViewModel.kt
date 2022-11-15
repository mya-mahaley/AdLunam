package com.example.adlunam.ui.images

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adlunam.OneImage
import com.example.adlunam.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ImagesViewModel : ViewModel() {

    var fetchDone : MutableLiveData<Boolean> = MutableLiveData(false)
    private val imagesApi = NasaImageApi.create()
    private val imagesRepository = NasaImageRepository(imagesApi)

    private val nasaImages = MutableLiveData<List<NasaImage>>()
    private val searchTerm = MutableLiveData<String>().apply {
        value = "moon"
    }

    init{
        netRefresh()
    }

    fun netRefresh() {
        // XXX Write me.  This is where the network request is initiated.
        fetchDone.value = false
        viewModelScope.launch (
            context = viewModelScope.coroutineContext +
                    Dispatchers.IO) {
            nasaImages.postValue(imagesRepository.fetchImages(searchTerm.value!!))
        }
    }

    fun observeImages(): LiveData<List<NasaImage>> {
        return nasaImages
    }

    // Observe searchTerm livedata
    fun observeSearchTerm(): LiveData<String> { return searchTerm }

    fun setSearchTerm(newTerm: String){
        searchTerm.value = newTerm
    }

    // Convenient place to put it as it is shared
    companion object {
        fun doOneImage(context: Context, imageData: NasaImage) {
            var intent = Intent(context, OneImage::class.java).apply{
                putExtra(ImageAdapter.titleKey, imageData.title)
                putExtra(ImageAdapter.descriptionKey, imageData.description)
                putExtra(ImageAdapter.imageURLKey, imageData.url)
            }
            context.startActivity(intent)
        }
    }


}