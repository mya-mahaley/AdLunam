 package com.example.adlunam.api

import com.google.gson.annotations.SerializedName
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


 //https://api.openweathermap.org/data/3.0/onecall?lat=30.267153&lon=-97.743057&appid=d304d12e4d3059f58c1134409bdc1f4e
interface WeatherApi {
    @GET("/data/3.0/onecall?appid=d304d12e4d3059f58c1134409bdc1f4e")
    suspend fun getCurrentWeather(@Query("lat") lat: String, @Query("lon") lon: String): WeatherResponse

     data class WeatherResponse (
         @SerializedName("daily") var daily : ArrayList<Weather> = arrayListOf()
     )

    companion object {
        var url = HttpUrl.Builder()
            .scheme("https")
            .host("api.openweathermap.org")
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