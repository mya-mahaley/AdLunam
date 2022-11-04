package com.example.adlunam.ui.trivia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TriviaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Trivia Fragment"
    }
    val text: LiveData<String> = _text
}