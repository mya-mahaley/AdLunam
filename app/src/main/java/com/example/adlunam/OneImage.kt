package com.example.adlunam

import android.app.Activity
import android.content.Intent
import com.example.adlunam.ui.images.ImageAdapter
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.adlunam.databinding.ActivityOneImageBinding
import com.example.adlunam.glide.Glide

class OneImage: AppCompatActivity() {
    private lateinit var binding:ActivityOneImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val oneImageBinding = ActivityOneImageBinding.inflate(layoutInflater)
        setContentView(oneImageBinding.root)

        // Get the support ActionBar corresponding to this toolbar
        setSupportActionBar(oneImageBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //val actionBar = supportActionBar
        //actionBar?.setDisplayHomeAsUpEnabled(true)

        val activityThatCalled = intent
        val description = activityThatCalled.getStringExtra(ImageAdapter.descriptionKey)
        val imageURL = activityThatCalled.getStringExtra(ImageAdapter.imageURLKey)
        val title = activityThatCalled.getStringExtra(ImageAdapter.titleKey)

        // Add all the stuff
        oneImageBinding.toolbarTitle.text = title
        oneImageBinding.postTextTitle.text = title
        oneImageBinding.selfText.text = description
        // Glide stuff
        Glide.glideFetch(imageURL!!, imageURL!!, oneImageBinding.postImage)
    }
}