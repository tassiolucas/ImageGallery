package com.codescreen.image_gallery.domain.model.repository

import com.codescreen.image_gallery.domain.model.PhotoInfo
import com.codescreen.image_gallery.networking.Networking
import com.codescreen.image_gallery.services.repository.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networking: Networking
) : Repository {

    override suspend fun getPhotoList(page: Int, photoList: MutableList<PhotoInfo>) = flow {
        emit(Resource.Loading())

        try {
            val response = networking.getPhotos(page = page)

            if (response.isSuccessful) {
                response.body()?.photos?.photo?.map { photoInfo ->
                    val secondResponse = networking.getSizes(photoInfo.id)
                    if (secondResponse.isSuccessful) {
                        secondResponse.body()?.sizes?.size?.firstOrNull { it.label == SIZE }?.source?.let { url ->
                            photoList.add(
                                PhotoInfo(
                                    title = photoInfo.title,
                                    url = url
                                )
                            )
                        }
                    } else {
                        throw HttpException(secondResponse)
                    }
                }
            } else {
                throw HttpException(response)
            }
            emit(Resource.Success(photoList.toList()))
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: UNKNOWN_ERROR
                emit(Resource.Error(errorMessage))
            } else {
                emit(Resource.Error(UNKNOWN_ERROR))
            }
        }
    }

    private companion object {
        const val SIZE = "Large Square"
        const val UNKNOWN_ERROR = "Unknown Error"
    }
}