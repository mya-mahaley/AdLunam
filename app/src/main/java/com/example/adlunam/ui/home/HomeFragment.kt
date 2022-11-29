package com.example.adlunam.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.adlunam.databinding.FragmentHomeBinding
import com.example.adlunam.glide.Glide
import com.google.firebase.auth.FirebaseAuth

//https://www.programmableweb.com/api/mooncalc-rest-api
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private var locationPermissionGranted = false
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.apotdHeader.visibility = View.INVISIBLE

        homeViewModel.observePicture().observe(viewLifecycleOwner) {
            if(it != null){
                binding.username.text = FirebaseAuth.getInstance().currentUser.displayName + ":"
                binding.title.text = it.title
                Glide.glideFetch(it.url, it.url, binding.spaceImage)
                homeViewModel.fetchDone.postValue(true)
            }
        }

        homeViewModel.observeFetchDone().observe(viewLifecycleOwner) {
            if(it){
                binding.apotdHeader.visibility = View.VISIBLE
            } else {
                binding.apotdHeader.visibility = View.INVISIBLE
            }
        }

        requestPermission()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getLocation() {
        locationManager = getSystemService(this.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun requestPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    locationPermissionGranted = true
                    getLastLocation()
                } else -> {
                Toast.makeText(requireContext(),
                    "Unable to show location - permission required",
                    Toast.LENGTH_LONG).show()
            }
            }
        }
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
    }
}