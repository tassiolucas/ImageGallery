package com.codescreen.image_gallery.domain.model.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.codescreen.image_gallery.domain.model.PhotosPagingSource
import com.codescreen.image_gallery.networking.Networking
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networking: Networking
) : Repository {
    override fun getPhotoList(page: Int, perPage: Int) = Pager(
        config = PagingConfig(
            pageSize = perPage
        ),
        pagingSourceFactory = {
            PhotosPagingSource(networking, perPage)
        }
    ).flow
}