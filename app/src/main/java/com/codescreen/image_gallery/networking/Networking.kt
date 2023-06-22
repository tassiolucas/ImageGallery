package com.codescreen.image_gallery.networking

import com.codescreen.image_gallery.networking.responses.PhotosGalleryResponse
import com.codescreen.image_gallery.networking.responses.SizesGalleryResponse
import retrofit2.Response

interface Networking {
    suspend fun getPhotos(page: Int, perPage: Int): Response<PhotosGalleryResponse>
    suspend fun getSizes(photoId: String): Response<SizesGalleryResponse>
}