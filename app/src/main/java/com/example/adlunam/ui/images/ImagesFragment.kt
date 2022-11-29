package com.example.adlunam.ui.images

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.adlunam.databinding.FragmentImageBinding


// https://astropical.space/quiz.php?category=astronomy+1

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
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = binding.recyclerView

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        val adapter = ImageAdapter(viewModel)
        recyclerView.adapter = adapter

        viewModel.observeImages().observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        viewModel.observeSearchTerm().observe(viewLifecycleOwner){
            viewModel.netRefresh()
        }

        binding.searchBar.addTextChangedListener {
            val text = binding?.searchBar?.text.toString()
            if(text == ""){
                hideKeyboard()
            }
            viewModel.setSearchTerm(text)
        }

        binding.searchBar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideKeyboard()
                return@OnKeyListener true
            }
            false
        })

        /*binding.searchBar.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.d("XXX", "IME ACTION SEARCH")
                val text = binding?.searchBar?.text.toString()
                viewModel.setSearchTerm(text)
                hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })*/
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}