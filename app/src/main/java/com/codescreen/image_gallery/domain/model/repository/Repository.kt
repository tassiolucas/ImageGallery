package com.codescreen.image_gallery.domain.model.repository

import com.codescreen.image_gallery.domain.model.PhotoInfo
import com.codescreen.image_gallery.services.repository.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getPhotoList(
        page: Int,
        photoList: MutableList<PhotoInfo>
    ): Flow<Resource<List<PhotoInfo>>>
}