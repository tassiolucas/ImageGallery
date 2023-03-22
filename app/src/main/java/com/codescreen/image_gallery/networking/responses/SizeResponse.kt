package com.codescreen.image_gallery.networking.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SizeResponse(
    @SerializedName("label")
    var label: String,
    @SerializedName("width")
    var width: Int,
    @SerializedName("height")
    var height: Int,
    @SerializedName("source")
    var source: String,
    @SerializedName("media")
    var media: String
) : Serializable
