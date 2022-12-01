package com.example.adlunam.ui.home

import com.example.adlunam.R
import android.content.Intent
import android.os.Build
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.adlunam.databinding.FragmentHomeBinding
import com.google.android.gms.location.*
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import com.example.adlunam.*


//https://www.programmableweb.com/api/mooncalc-rest-api
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //binding.apotdHeader.visibility = View.INVISIBLE
        binding.welcomeText.animation = AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in)

        mainViewModel.observeDisplayName().observe(viewLifecycleOwner){
            binding.displayName.text = it
        }
        binding.planetModelsButon.setOnClickListener {
            var popupMenu = PopupMenu(requireContext(), it)
            popupMenu.menuInflater.inflate(R.menu.planet_options_menu, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.mercury_model -> {
                        var intent = Intent(context, ModelTester::class.java)
                        intent.putExtra("planet", "mercury")
                        startActivity(intent)
                        true
                    }
                    R.id.venus_model -> {
                        var intent = Intent(context, ModelTester::class.java)
                        intent.putExtra("planet", "venus")
                        startActivity(intent)
                        true
                    }
                    R.id.earth_model -> {
                        var intent = Intent(context, ModelTester::class.java)
                        intent.putExtra("planet", "earth")
                        startActivity(intent)
                        true
                    }
                    R.id.mars_model -> {
                        var intent = Intent(context, ModelTester::class.java)
                        intent.putExtra("planet", "mars")
                        startActivity(intent)
                        true
                    }
                    R.id.jupiter_model -> {
                        var intent = Intent(context, ModelTester::class.java)
                        intent.putExtra("planet", "jupiter")
                        startActivity(intent)
                        true
                    }
                    R.id.saturn_model-> {
                        var intent = Intent(context, ModelTester::class.java)
                        intent.putExtra("planet", "saturn")
                        startActivity(intent)
                        true
                    }
                    R.id.uranus_model -> {
                        var intent = Intent(context, ModelTester::class.java)
                        intent.putExtra("planet", "uranus")
                        startActivity(intent)
                        true
                    }
                    R.id.neptune_model -> {
                        var intent = Intent(context, ModelTester::class.java)
                        intent.putExtra("planet", "neptune")
                        startActivity(intent)
                        true
                    }
                    R.id.moon_model -> {
                        var intent = Intent(context, ModelTester::class.java)
                        intent.putExtra("planet", "moon")
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
        }

        binding.pictureOfTheDayButton.setOnClickListener {
            val intent = Intent(context, PictureOfTheDayActivity::class.java)
            startActivity(intent)
        }

        with(binding.planetModel) {
            setBackgroundColor(Color.TRANSPARENT)
            //setBackgroundResource(R.drawable.)
            loadUrl(getString(com.example.adlunam.R.string.solar_system_model_location))
            settings.apply {
                javaScriptEnabled = true
                loadWithOverviewMode = true
            }
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}