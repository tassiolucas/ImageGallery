package com.codescreen.image_gallery.networking.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SizesGalleryResponse(
    @SerializedName("sizes")
    var sizes: SizesResponse
) : Serializable