package com.example.adlunam.api

import com.google.gson.annotations.SerializedName

data class PictureOfTheDay (
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("explanation")
    val explanation: String,
)