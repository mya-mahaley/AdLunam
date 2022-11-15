package com.example.adlunam.api

class WeatherRepository(private val api: PictureOfTheDayApi) {
    suspend fun fetchMoonPhase(): PictureOfTheDay {
        return api.getPictureOfTheDay()
    }
}
