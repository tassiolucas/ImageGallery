package com.codescreen.image_gallery.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codescreen.image_gallery.ui.ImageGalleryViewModel.ImageGalleryScreenState.*
import com.codescreen.image_gallery.ui.gallery_list.GalleryListScreen
import com.codescreen.image_gallery.ui.item_detail.ItemDetailScreen
import com.codescreen.image_gallery.ui.theme.ImageGalleryTheme
import com.codescreen.image_gallery.ui.widgets.GalleryTopAppBar
import com.codescreen.image_gallery.utils.onBackPressed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageGalleryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageGalleryTheme {
                CurrentImageGallery(this)
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CurrentImageGallery(
    activity: ImageGalleryActivity,
    viewModel: ImageGalleryViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val list by viewModel.photos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val photoSelected by viewModel.photoSelected.collectAsState()
    val scrollState: LazyGridState = rememberLazyGridState()

    activity.onBackPressed {
        viewModel.goBack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            GalleryTopAppBar(
                title = photoSelected?.title,
                showGoBack = state == ItemDetail,
                onGoBackClick = {
                    viewModel.goBack()
                }
            )
        },
    ) {
        when (state) {
            is GalleryList -> {
                GalleryListScreen(
                    items = list,
                    state = scrollState,
                    isLoading = isLoading,
                    onItemSelected = { photoSelected ->
                        viewModel.selectItem(photoSelected)
                    }
                ) {
                    viewModel.addPage()
                }
            }
            is ItemDetail -> {
                photoSelected?.let {
                    ItemDetailScreen(
                        imageUrl = it.url
                    )
                }
            }
        }
    }
}