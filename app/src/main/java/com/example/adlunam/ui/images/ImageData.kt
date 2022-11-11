package com.example.adlunam.ui.images

class ImageData(val text:String, val imageURL:String) {
    private val _text = text
    private val _imageURL = imageURL

    fun getSpaceImageText():String {
        return _text
    }
    fun getSpaceImageURL():String{
        return _imageURL
    }
}
