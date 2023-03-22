package com.codescreen.image_gallery.networking.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SizesResponse(
    @SerializedName("canblog")
    var canblog: Int,
    @SerializedName("canprint")
    var canprint: Int,
    @SerializedName("candownload")
    var candownload: Int,
    @SerializedName("size")
    var size: List<SizeResponse>
) : Serializable
