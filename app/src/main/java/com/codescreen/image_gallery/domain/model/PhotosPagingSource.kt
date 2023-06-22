package com.codescreen.image_gallery.domain.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.codescreen.image_gallery.networking.Networking

class PhotosPagingSource(
    private val networking: Networking,
    private val pageSize: Int
) : PagingSource<Int, PhotoInfo>() {

    override fun getRefreshKey(state: PagingState<Int, PhotoInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoInfo> {
        return try {
            val page = params.key ?: 1
            val response = networking.getPhotos(page = page, perPage = pageSize)
            val photoList = mutableListOf<PhotoInfo>()

            if (response.isSuccessful) {
                response.body()?.photos?.photo?.map { photoInfo ->
                    val secondResponse = networking.getSizes(photoId = photoInfo.id)
                    if (secondResponse.isSuccessful) {
                        secondResponse.body()?.sizes?.size?.firstOrNull { it.label == SIZE }?.source?.let { url ->
                            photoList.add(
                                PhotoInfo(
                                    title = photoInfo.title,
                                    url = url
                                )
                            )
                        }
                    }
                }
            }

            LoadResult.Page(
                data = photoList.toList(),
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (photoList.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private companion object {
        const val SIZE = "Large Square"
    }
}