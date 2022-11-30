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

class ProfileViewModel : ViewModel() {
    var fetchDone : MutableLiveData<Boolean> = MutableLiveData(false)
    private val weatherApi = WeatherApi.create()
    private val weatherRepository = WeatherRepository(weatherApi)
    private val weather = MutableLiveData<Weather>()

    private val nasaImages = MutableLiveData<List<NasaImage>>()
    private val searchTerm = MutableLiveData<String>().apply {
        value = "moon"
    }

    private var displayName = MutableLiveData("Uninitialized")
    private var email = MutableLiveData("Uninitialized")
    private var uid = MutableLiveData("Uninitialized")

    fun refreshMoon(long: Double, lat: Double) {
        // XXX Write me.  This is where the network request is initiated.
        fetchDone.value = false
        viewModelScope.launch (
            context = viewModelScope.coroutineContext +
                    Dispatchers.IO) {
            weather.postValue(weatherRepository.fetchCurrentWeather(lat.toString(), long.toString()))
        }
    }

    fun observeFetchDone(): LiveData<Boolean> {
        return fetchDone
    }

    fun observeWeather(): LiveData<Weather> {
        return weather
    }

    private fun userLogout() {
        displayName.postValue("No user")
        email.postValue("No email, no active user")
        uid.postValue("No uid, no active user")
    }

    fun updateUser() {
        // XXX Write me. Update user data in view model
        val user = FirebaseAuth.getInstance().currentUser
        email.postValue(user.email)
        uid.postValue(user.uid)
        displayName.postValue(user.displayName)
    }

    fun observeDisplayName() : LiveData<String> {
        return displayName
    }
    fun observeEmail() : LiveData<String> {
        return email
    }
    fun observeUid() : LiveData<String> {
        return uid
    }
    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        userLogout()
    }
}