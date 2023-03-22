package com.codescreen.image_gallery.ui.gallery_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codescreen.image_gallery.ui.theme.ImageGalleryTheme

@Composable
fun LoadingItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colors.primary
        )
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable
fun LoadingItemPreview() {
    ImageGalleryTheme {
        LoadingItem()
    }
}