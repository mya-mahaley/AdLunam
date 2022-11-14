package com.example.adlunam.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.adlunam.databinding.FragmentHomeBinding
import com.example.adlunam.glide.Glide

//https://www.programmableweb.com/api/mooncalc-rest-api
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

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