package com.example.adlunam.api

import android.util.Log
import androidx.core.text.toSpannable
import com.example.adlunam.ui.images.ImageData


class NasaImageRepository(private val api: NasaImageApi) {
    suspend fun fetchImages(searchTerm: String): List<NasaImage>{
        return unpackImageApiResponse(api.getImages(searchTerm))
    }

    private fun unpackImageApiResponse(response: NasaImageApi.NasaImageResponse): MutableList<NasaImage> {
        val items = response.collection?.items
        val list: MutableList<NasaImage> = mutableListOf()

        if (items != null) {
            for (item in items){
                val links = item.links[0]
                val data = item.data[0]
                val image = NasaImage(data.title!!, data.description!!, data.nasaId!!, links.href!!)
                list.add(image)
            }
        }

        return list
    }

}
