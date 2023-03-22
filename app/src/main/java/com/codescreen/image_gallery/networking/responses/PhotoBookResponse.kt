package com.codescreen.image_gallery.networking.responses

import com.google.gson.annotations.SerializedName

data class PhotoBookResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("pages")
    var pages: Int,
    @SerializedName("perpage")
    var perpage: Int,
    @SerializedName("total")
    var total: Int,
    @SerializedName("photo")
    var photo: List<PhotoResponse>
)