package com.codescreen.image_gallery.ui.item_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.codescreen.image_gallery.ui.theme.ImageGalleryTheme
import com.codescreen.image_gallery.ui.utils.photoItem

@Composable
fun ItemDetailSlot(
    modifier: Modifier = Modifier,
    imageUrl: String,
    imageContentScale: ContentScale = ContentScale.Crop,
    isLoading: Boolean = true,
    onIsLoadingChange: ((Boolean) -> Unit)? = null,
) {
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onLoading = {
            onIsLoadingChange?.invoke(true)
        }
    )

    LaunchedEffect(painter) {
        onIsLoadingChange?.invoke(false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colors.primary
            )
        } else {
            Image(
                modifier = modifier.fillMaxSize(),
                painter = painter,
                contentDescription = null,
                contentScale = imageContentScale
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable
fun ItemDetailSlotPreview() {
    val isLoading = remember { mutableStateOf(false) }

    ImageGalleryTheme {
        ItemDetailSlot(
            imageUrl = photoItem.url,
            isLoading = isLoading.value,
            onIsLoadingChange = {
                isLoading.value = it
            }
        )
    }
}