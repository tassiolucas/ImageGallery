package com.codescreen.image_gallery.domain.model.repository

import androidx.paging.PagingData
import com.codescreen.image_gallery.domain.model.PhotoInfo
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getPhotoList(
        page: Int,
        perPage: Int
    ): Flow<PagingData<PhotoInfo>>
}