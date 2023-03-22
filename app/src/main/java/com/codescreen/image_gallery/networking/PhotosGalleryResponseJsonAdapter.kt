package com.codescreen.image_gallery.networking

import com.codescreen.image_gallery.networking.responses.PhotoBookResponse
import com.codescreen.image_gallery.networking.responses.PhotosGalleryResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.*

class PhotosGalleryResponseJsonAdapter :
    JsonAdapter<PhotosGalleryResponse>() {

    @FromJson
    override fun fromJson(reader: JsonReader): PhotosGalleryResponse {
        var photos: PhotoBookResponse? = null
        var stat: String? = null
        reader.beginObject()

        while (reader.hasNext()) {
            when (reader.nextName()) {
                "photos" -> {
                    val type = object : TypeToken<PhotoBookResponse>() {}.type
                    photos = Gson().fromJson(reader.nextString(), type)
                }
                "stat" -> stat = reader.nextString()
                else -> reader.skipValue()
            }
        }
        reader.endObject()
        return PhotosGalleryResponse(
            photos = photos ?: PhotoBookResponse(
                page = 0,
                pages = 0,
                perpage = 0,
                total = 0,
                photo = listOf()
            )
        )
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: PhotosGalleryResponse?) {
        writer.beginObject()
        val type = object : TypeToken<List<PhotoBookResponse>>() {}.type
        writer.name("photos").value(Gson().toJson(writer, type))
        writer.endObject()
    }
}