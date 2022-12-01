package com.example.adlunam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adlunam.api.PictureOfTheDay
import com.example.adlunam.api.PictureOfTheDayApi
import com.example.adlunam.api.PictureOfTheDayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PotdViewModel : ViewModel() {
    var fetchDone : MutableLiveData<Boolean> = MutableLiveData(false)
    private val pictureApi = PictureOfTheDayApi.create()
    private val pictureRepository = PictureOfTheDayRepository(pictureApi)
    private val picture = MutableLiveData<PictureOfTheDay>()
    init {
        netRefresh()
    }

    fun netRefresh() {
        // XXX Write me.  This is where the network request is initiated.
        fetchDone.value = false
        viewModelScope.launch (
            context = viewModelScope.coroutineContext +
                    Dispatchers.IO) {
            picture.postValue(pictureRepository.fetchPictureOfTheDay())
        }
    }

    // XXX Another function is necessary
    fun observePicture(): LiveData<PictureOfTheDay> {
        return picture
    }

    fun observeFetchDone(): LiveData<Boolean> {
        return fetchDone
    }
}