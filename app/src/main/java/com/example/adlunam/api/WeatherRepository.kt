package com.example.adlunam.api

class WeatherRepository(private val api: WeatherApi) {
    suspend fun fetchCurrentWeather(lat: String, lon: String): Weather? {
        return unpackWeatherResponse(api.getCurrentWeather(lat, lon))
    }

    private fun unpackWeatherResponse(response: WeatherApi.WeatherResponse): Weather? {
        val daily = response.daily
        return if(daily.isNotEmpty()){
            daily[0]
        } else  {
            null
        }
    }
}
