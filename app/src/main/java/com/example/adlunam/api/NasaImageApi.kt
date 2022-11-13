package com.example.adlunam.api

import android.text.SpannableString
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.reflect.Type


interface NasaImageApi {
    // XXX Write me, two function prototypes with Retrofit annotations
    // @GET contains a string appended to the base URL
    // the string is called a path name
    // You can add a parameter to the path name like this
    // @GET("/r/{subreddit}/")
    // suspend fun getPosts(@Path("subreddit") subreddit: String) : xxxxxx
    // The reddit api docs are here: https://www.reddit.com/dev/api/#GET_hot
    @GET("/search?q={q}?media_type=image")
    suspend fun getImages(@Path("q") q: String) : ListingResponse

    // https://www.reddit.com/dev/api/#listings
    class ListingResponse(val data: ListingData)

    class ListingData(
        val items: List<RedditChildrenResponse>,
    )
    data class ImageApiResponse(val data: NasaImage)

    class SpannableDeserializer : JsonDeserializer<SpannableString> {
        // @Throws(JsonParseException::class)
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): SpannableString {
            return SpannableString(json.asString)
        }
    }

    companion object {
        // Tell Gson to use our SpannableString deserializer
        private fun buildGsonConverterFactory(): GsonConverterFactory {
            val gsonBuilder = GsonBuilder().registerTypeAdapter(
                SpannableString::class.java, SpannableDeserializer()
            )
            return GsonConverterFactory.create(gsonBuilder.create())
        }
        // Keep the base URL simple
        //private const val BASE_URL = "https://images-api.nasa.gov/"
        var httpurl = HttpUrl.Builder()
            .scheme("https")
            .host("www.images-api.nasa.gov")
            .build()
        fun create(): NasaImageApi = create(httpurl)
        private fun create(httpUrl: HttpUrl): NasaImageApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    // Enable basic HTTP logging to help with debugging.
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(buildGsonConverterFactory())
                .build()
                .create(NasaImageApi::class.java)
        }
    }
}