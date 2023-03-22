package com.codescreen.image_gallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codescreen.image_gallery.domain.model.PhotoInfo
import com.codescreen.image_gallery.domain.model.repository.Repository
import com.codescreen.image_gallery.services.repository.Resource.Loading
import com.codescreen.image_gallery.services.repository.Resource.Success
import com.codescreen.image_gallery.ui.ImageGalleryViewModel.ImageGalleryScreenState.GalleryList
import com.codescreen.image_gallery.ui.ImageGalleryViewModel.ImageGalleryScreenState.ItemDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageGalleryViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ImageGalleryScreenState>(GalleryList)
    val uiState: StateFlow<ImageGalleryScreenState> = _uiState

    private val _photos = MutableStateFlow<List<PhotoInfo>>(listOf())
    val photos: StateFlow<List<PhotoInfo>> = _photos

    private val _photoSelected = MutableStateFlow<PhotoInfo?>(null)
    val photoSelected: StateFlow<PhotoInfo?> = _photoSelected

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _pageIndex = MutableStateFlow(1)

    private val _list = mutableListOf<PhotoInfo>()

    init {
        getListPhotoInfo()
    }

    fun addPage() {
        val cards = _list.count()

        if (cards % PAGE_ITEMS_COUNT == 0 && cards >= PAGE_ITEMS_COUNT && !_isLoading.value) {
            _pageIndex.value++
            getListPhotoInfo()
        }
    }

    fun selectItem(photo: PhotoInfo) {
        _uiState.value = ItemDetail
        _photoSelected.value = photo
    }

    fun goBack() {
        _uiState.value = GalleryList
        _photoSelected.value = null
    }

    private fun getListPhotoInfo() {
        viewModelScope.launch {
            repository.getPhotoList(_pageIndex.value, _list).collect { resource ->
                when (resource) {
                    is Loading -> {
                        _isLoading.value = true
                    }
                    is Success -> {
                        resource.data?.let {
                            _photos.value = it
                        }
                        _isLoading.value = false
                    }
                    else -> {
                        _isLoading.value = false
                        resource.message
                    }
                }
            }
        }
    }

    sealed class ImageGalleryScreenState {
        object GalleryList : ImageGalleryScreenState()
        object ItemDetail : ImageGalleryScreenState()
    }

    private companion object {
        const val PAGE_ITEMS_COUNT = 100
    }
}