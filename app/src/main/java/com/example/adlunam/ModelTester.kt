package com.example.adlunam

import android.os.Bundle
import android.view.MenuItem
import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import com.example.adlunam.databinding.ActivityOneImageBinding
import com.example.adlunam.glide.Glide
import com.example.adlunam.ui.images.ImageAdapter
import com.example.adlunam.R
import com.example.adlunam.databinding.ModelViewerBinding

class ModelTester :AppCompatActivity(){

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modelViewBinding = ModelViewerBinding.inflate(layoutInflater)
        setContentView(modelViewBinding.root)

        // Get the support ActionBar corresponding to this toolbar
        setSupportActionBar(modelViewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val activityThatCalled = intent
        val planetName = activityThatCalled.getStringExtra("planet")

        //val actionBar = supportActionBar
        //actionBar?.setDisplayHomeAsUpEnabled(true)
        with(modelViewBinding.modelWebView) {
            setBackgroundColor(Color.TRANSPARENT)
            when(planetName){
                "mercury" -> loadUrl(getString(R.string.mercury_model_location))
                "venus" -> loadUrl(getString(R.string.venus_model_location))
                "earth" -> loadUrl(getString(R.string.earth_model_location))
                "mars" -> loadUrl(getString(R.string.mars_model_location))
                "jupiter" -> loadUrl(getString(R.string.jupiter_model_location))
                "saturn" -> loadUrl(getString(R.string.saturn_model_location))
                "uranus" -> loadUrl(getString(R.string.uranus_model_location))
                "neptune" -> loadUrl(getString(R.string.neptune_model_location))
                "moon" -> loadUrl(getString(R.string.moon_model_location))
            }
            settings.apply {
                javaScriptEnabled = true
                loadWithOverviewMode = true
            }
        }

    }

    // Tell us when the back button is pressed
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}