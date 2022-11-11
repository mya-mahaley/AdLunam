package com.example.adlunam.ui.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adlunam.databinding.FragmentImageBinding


class ImagesFragment : Fragment() {

    private val viewModel: ImagesViewModel by activityViewModels()
    private var _binding: FragmentImageBinding? = null

    companion object {
        fun newInstance(): ImagesFragment {
            return ImagesFragment()
        }
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val imagesViewModel =
            ViewModelProvider(this).get(ImagesViewModel::class.java)

        _binding = FragmentImageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = ImageAdapter(viewModel)
        recyclerView.adapter = adapter


        /*viewModel.observeImages().observe(viewLifecycleOwner){
            adapter.submitList(it)
        }*/

        adapter.submitList(viewModel.images)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}