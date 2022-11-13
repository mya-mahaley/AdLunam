package com.example.adlunam.api

import android.text.SpannableString
import com.google.gson.GsonBuilder
import edu.cs371m.reddit.MainActivity

class NasaImageRepository(private val nasaImageApi: NasaImageApi) {
    // NB: This is for our testing.
    val gson = GsonBuilder().registerTypeAdapter(
            SpannableString::class.java, NasaImageApi.SpannableDeserializer()
        ).create()

    private fun unpackPosts(response: NasaImageApi.ListingResponse): List<NasaImageApi> {
        // XXX Write me.
        val children = response.data.children
        val posts = mutableListOf<RedditPost>()
        for (c in children) {
            posts.add(c.data)
        }
        return posts
    }

    suspend fun getPosts(subreddit: String): List<RedditPost> {
        return if (MainActivity.globalDebug) {
            val response = gson.fromJson(
                MainActivity.jsonAww100,
                RedditApi.ListingResponse::class.java)
            unpackPosts(response)
        } else {
            // XXX Write me.
            unpackPosts(redditApi.getPosts(subreddit))
        }
    }

    suspend fun getSubreddits(): List<RedditPost> {
        return unpackPosts(nasaImageApi.getSubreddits())

    }
}
