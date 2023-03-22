package com.codescreen.image_gallery.networking.responses

import com.google.gson.annotations.SerializedName

data class PhotosGalleryResponse(
    @SerializedName("photos")
    var photos: PhotoBookResponse
)