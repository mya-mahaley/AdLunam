package com.example.adlunam.api

class PictureOfTheDayRepository(private val api: PictureOfTheDayApi) {
    suspend fun fetchPictureOfTheDay(): PictureOfTheDay {
        return api.getPictureOfTheDay()
    }
}
