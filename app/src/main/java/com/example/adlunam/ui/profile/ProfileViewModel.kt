package com.example.adlunam.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adlunam.api.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//https://stackoverflow.com/questions/50047863/fusedlocationproviderclient-lastlocation-addonsuccesslistener-always-null

class ProfileViewModel : ViewModel() {
    var fetchDone : MutableLiveData<Boolean> = MutableLiveData(false)
    private val weatherApi = WeatherApi.create()
    private val weatherRepository = WeatherRepository(weatherApi)
    private val weather = MutableLiveData<Weather>()

    private val nasaImages = MutableLiveData<List<NasaImage>>()
    private val searchTerm = MutableLiveData<String>().apply {
        value = "moon"
    }

    fun refreshMoon(long: Double, lat: Double) {
        // XXX Write me.  This is where the network request is initiated.
        fetchDone.value = false
        viewModelScope.launch (
            context = viewModelScope.coroutineContext +
                    Dispatchers.IO) {
            weather.postValue(weatherRepository.fetchCurrentWeather(lat.toString(), long.toString()))
        }
    }


    fun observeWeather(): LiveData<Weather> {
        return weather
    }
}