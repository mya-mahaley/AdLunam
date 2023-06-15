package com.example.adlunam.ui.about

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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.adlunam.AuthInit
import com.example.adlunam.MainViewModel
import com.example.adlunam.databinding.FragmentAboutBinding
import com.example.adlunam.databinding.FragmentProfileBinding
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlin.getValue


//https://www.programmableweb.com/api/mooncalc-rest-api
//https://stackoverflow.com/questions/50047863/fusedlocationproviderclient-lastlocation-addonsuccesslistener-always-null
class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("XXX", "PROFILE CREATED")
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.christianDescription.text = " I am a Computer Science Senior attending the " +
            "University of Texas at Austin. I am currently working at Juni Learning as a Part-Time " +
            "Computer Science Instructor. My favorite part of this project was learning how to use " +
            "the model-viewer to display 3D models of different planets!"

        binding.myaDescription.text = " Hi! I am  a Senior at the University of Texas at " +
                "Austin studying Computer Science. I have a passion for Mobile and " +
                "Frontend development. My favorite part of this project was developing " +
                "the trivia game and making a worthwhile experience for the user!"

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
