package com.codescreen.image_gallery.ui.item_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.codescreen.image_gallery.ui.theme.ImageGalleryTheme
import com.codescreen.image_gallery.ui.utils.photoItem

@Composable
fun ItemDetailScreen(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {
    val isLoading = rememberSaveable { mutableStateOf(false) }

    ItemDetailSlot(
        modifier = modifier,
        imageUrl = imageUrl,
        imageContentScale = ContentScale.Fit,
        isLoading = isLoading.value,
        onIsLoadingChange = {
            isLoading.value = it
        }
    )
}

@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable
fun ItemDetailScreenPreview() {
    ImageGalleryTheme {
        ItemDetailScreen(
            imageUrl = photoItem.url,
        )
    }
}