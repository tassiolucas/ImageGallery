package com.codescreen.image_gallery.networking

import com.codescreen.image_gallery.networking.responses.PhotosGalleryResponse
import com.codescreen.image_gallery.networking.responses.SizesGalleryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("rest/")
    suspend fun getPhotos(
        @Query("method") method: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("api_key") apiKey: String = "f9cc014fa76b098f9e82f1c288379ea1",
        @Query("tags") tags: String = "kitten",
        @Query("format") json: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1
    ): Response<PhotosGalleryResponse>

    @GET("rest/")
    suspend fun getSizes(
        @Query("method") method: String,
        @Query("photo_id") photoId: String,
        @Query("api_key") apiKey: String = "f9cc014fa76b098f9e82f1c288379ea1",
        @Query("format") json: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1
    ): Response<SizesGalleryResponse>
}