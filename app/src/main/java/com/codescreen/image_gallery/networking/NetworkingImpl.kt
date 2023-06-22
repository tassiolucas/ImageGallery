package com.codescreen.image_gallery.networking

import javax.inject.Inject

internal class NetworkingImpl @Inject constructor(
    private val api: Api
) : Networking {

    override suspend fun getPhotos(page: Int, perPage: Int) = api.getPhotos(methodSearch, page, perPage)

    override suspend fun getSizes(photoId: String) = api.getSizes(methodSizes, photoId)

    companion object {
        var methodSearch = "flickr.photos.search"
        var methodSizes = "flickr.photos.getSizes"
    }
}