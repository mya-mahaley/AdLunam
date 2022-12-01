package com.example.adlunam

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.adlunam.databinding.ActivityPotdBinding
import com.example.adlunam.glide.Glide

class PictureOfTheDayActivity: AppCompatActivity() {
    private val potdViewModel: PotdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPotdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the support ActionBar corresponding to this toolbar
        //setSupportActionBar(oneImageBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        potdViewModel.observePicture().observe(this) { picture ->
            if(picture != null){
                binding.title.text = picture.title
                Glide.glideFetch(picture.url, picture.url, binding.spaceImage)
                potdViewModel.fetchDone.postValue(true)
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