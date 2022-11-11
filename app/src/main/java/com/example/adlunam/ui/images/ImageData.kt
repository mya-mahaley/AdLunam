package com.example.adlunam.ui.images

class ImageData(val title:String, val description:String, val imageURL:String) {
    private val _title = title
    private val _imageURL = imageURL
    private val _description = description

    fun getSpaceImageTitle():String {
        return _title
    }
    fun getSpaceImageDescription():String {
        return _description
    }
    fun getSpaceImageURL():String{
        return _imageURL
    }
}
