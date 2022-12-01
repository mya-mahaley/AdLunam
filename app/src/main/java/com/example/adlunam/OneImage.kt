package com.example.adlunam

import android.app.Activity
import android.content.Intent
import com.example.adlunam.ui.images.ImageAdapter
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.adlunam.databinding.ActivityOneImageBinding
import com.example.adlunam.glide.Glide

class OneImage: AppCompatActivity() {
    private lateinit var binding:ActivityOneImageBinding

    // https://www.pexels.com/
    // https://dribbble.com/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val oneImageBinding = ActivityOneImageBinding.inflate(layoutInflater)
        setContentView(oneImageBinding.root)

        // Get the support ActionBar corresponding to this toolbar
        setSupportActionBar(oneImageBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val actionBar = supportActionBar
        //actionBar?.setDisplayHomeAsUpEnabled(true)

        val activityThatCalled = intent
        val description = activityThatCalled.getStringExtra(ImageAdapter.descriptionKey)
        val imageURL = activityThatCalled.getStringExtra(ImageAdapter.imageURLKey)
        val title = activityThatCalled.getStringExtra(ImageAdapter.titleKey)

        Log.d("XXX", "description: $description")
        Log.d("XXX", "title: $title")
        Log.d("XXX", "imageURL: $imageURL")
        // Add all the stuff
        oneImageBinding.imageTitle.text = title
        oneImageBinding.description.text = description
        // Glide stuff
        Glide.glideFetch(imageURL!!, imageURL!!, oneImageBinding.postImage)
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