package com.example.adlunam.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.adlunam.databinding.FragmentProfileBinding
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import kotlin.getValue


//https://www.programmableweb.com/api/mooncalc-rest-api
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var locationManager: LocationManager
    private lateinit var client: FusedLocationProviderClient
    private var latitude = 0.0
    private var longitude = 0.0


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("XXX", "PROFILE CREATED")
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        client = LocationServices.getFusedLocationProviderClient(requireContext())

        /*if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            getCurrentLocation()
        }*/

        getCurrentLocation()

        profileViewModel.observeWeather().observe(viewLifecycleOwner) {
            if(it != null){
                profileViewModel.fetchDone.postValue(true)
                binding.longLat.text = "$longitude, $latitude"
                val moonPhase = it.moonPhase
                var imageResId = root.resources.getIdentifier("@drawable/new_moon", "drawable", requireActivity().packageName)
                if(moonPhase == 0.0 || moonPhase == 1.0) {
                    binding.currentPhase.text = "Current Phase: New Moon"
                } else if (moonPhase!! > 0.0 && moonPhase < 0.25){
                    binding.currentPhase.text = "Current Phase: Waxing Crescent"
                    imageResId = root.resources.getIdentifier("@drawable/waxing_crescent", "drawable", requireActivity().packageName)
                } else if(moonPhase == 0.25) {
                    binding.currentPhase.text = "Current Phase: First Quarter"
                    imageResId = root.resources.getIdentifier("@drawable/first_quarter", "drawable", requireActivity().packageName)
                } else if (moonPhase!! > 0.25 && moonPhase < 0.5){
                    binding.currentPhase.text = "Current Phase: Waxing Gibbous"
                    imageResId = root.resources.getIdentifier("@drawable/waxing_gibbous", "drawable", requireActivity().packageName)
                } else if(moonPhase == 0.5) {
                    binding.currentPhase.text = "Current Phase: Full Moon"
                    imageResId = root.resources.getIdentifier("@drawable/full_moon", "drawable", requireActivity().packageName)
                } else if (moonPhase!! > 0.5 && moonPhase < 0.75){
                    binding.currentPhase.text = "Current Phase: Waxing Gibbous"
                    imageResId = root.resources.getIdentifier("@drawable/waxing_gibbous", "drawable", requireActivity().packageName)
                } else if(moonPhase == 0.75) {
                    binding.currentPhase.text = "Current Phase: Last Quarter"
                    imageResId = root.resources.getIdentifier("@drawable/last_quarter", "drawable", requireActivity().packageName)
                } else {
                    binding.currentPhase.text = "Current Phase: Waning Crescent"
                    imageResId = root.resources.getIdentifier("@drawable/waning_crescent", "drawable", requireActivity().packageName)
                }
                binding.moonPhaseImage.setImageResource(imageResId)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        // Initialize Location manager
        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // Check condition
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // When location service is enabled
            // Get last location

            client.lastLocation.addOnCompleteListener { task ->
                // Initialize location
                val location: Location? = task.result
                // Check condition
                if (location != null) {
                    longitude = location.longitude
                    latitude = location.latitude
                    profileViewModel.refreshMoon(longitude, latitude)
                } else {
                    // When location result is null
                    // initialize location request
                    val locationRequest: LocationRequest = LocationRequest()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(1)
                        .setFastestInterval(1)
                        .setNumUpdates(1)
                    // Initialize location call back
                    val locationCallback: LocationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            val newLocation: Location = locationResult.lastLocation
                            longitude = newLocation.longitude
                            latitude = newLocation.latitude
                            profileViewModel.refreshMoon(longitude, latitude)
                        }
                    }

                    // Request location updates
                    client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                }
            }

            /*.lastLocation.addOnCompleteListener { task ->

            }
            client.lastLocation.addOnCompleteListener(
                OnCompleteListener<Location> { task ->
                    // Initialize location
                    val location: Location? = task.result
                    // Check condition
                    if (location != null) {
                        longitude = location.longitude
                        latitude = location.latitude
                        profileViewModel.refreshMoon(longitude, latitude)
                    } else {
                        // When location result is null
                        // initialize location request
                        val locationRequest: LocationRequest = LocationRequest()
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(1)
                            .setFastestInterval(1)
                            .setNumUpdates(1)
                        // Initialize location call back
                        val locationCallback: LocationCallback = object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                val newLocation: Location = locationResult.lastLocation
                                longitude = newLocation.longitude
                                latitude = newLocation.latitude
                                profileViewModel.refreshMoon(longitude, latitude)
                            }
                        }

                        // Request location updates
                        client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                    }
                })*/

        } else {
            // When location service is not enabled
            // open location setting
            startActivity(
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

        Log.d("LOCATION RESULT", "$longitude $latitude")
    }
}