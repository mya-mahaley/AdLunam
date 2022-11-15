 package com.example.adlunam.api

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



interface WeatherApi {
    @GET("/planetary/apod?api_key=Zw1bd9LQyu3bLLS8s88cHZneUvKqBUgghIhTuYPP")
    suspend fun getPictureOfTheDay(): PictureOfTheDay

    companion object {
        var url = HttpUrl.Builder()
            .scheme("https")
            .host("api.nasa.gov")
            .build()

        // Public create function that ties together building the base
        // URL and the private create function that initializes Retrofit
        fun create(): WeatherApi = create(url)
        private fun create(httpUrl: HttpUrl): WeatherApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()

            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)

        }
    }
}