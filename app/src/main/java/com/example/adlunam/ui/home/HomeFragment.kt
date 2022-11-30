package com.example.adlunam.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.adlunam.databinding.FragmentHomeBinding
import com.example.adlunam.glide.Glide
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import java.lang.String
import java.util.*
import kotlin.Any
import kotlin.arrayOf
import kotlin.getValue


//https://www.programmableweb.com/api/mooncalc-rest-api
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var client: FusedLocationProviderClient


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.apotdHeader.visibility = View.INVISIBLE
        client = LocationServices.getFusedLocationProviderClient(requireContext())

        homeViewModel.observePicture().observe(viewLifecycleOwner) {
            if(it != null){
                //binding.username.text = FirebaseAuth.getInstance().currentUser.displayName + ":"
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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}