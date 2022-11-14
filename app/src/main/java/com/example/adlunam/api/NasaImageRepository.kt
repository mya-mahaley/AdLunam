package com.example.adlunam.api

import android.util.Log
import com.example.adlunam.ui.images.ImageData


class NasaImageRepository(private val api: NasaImageApi) {
    suspend fun fetchImages(searchTerm: String): List<NasaImage>{
        return unpackImageApiResponse(api.getImages(searchTerm))
    }

    private fun unpackImageApiResponse(response: NasaImageApi.ImageApiResponse): MutableList<NasaImage> {
        var collection = response.collection
        var items = collection.items
        var data = items.size
        Log.d("MYA", data.toString())
        val list: MutableList<NasaImage> = mutableListOf()
        return list
    }

}
