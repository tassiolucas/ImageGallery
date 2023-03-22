package com.codescreen.image_gallery.ui.gallery_list

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codescreen.image_gallery.domain.model.PhotoInfo
import com.codescreen.image_gallery.ui.item_detail.ItemDetailSlot
import com.codescreen.image_gallery.ui.theme.ImageGalleryTheme
import com.codescreen.image_gallery.ui.utils.photoItem

@Composable
fun GalleryItem(
    modifier: Modifier = Modifier,
    info: PhotoInfo,
    isLoading: Boolean = true,
    onIsLoadingChange: ((Boolean) -> Unit)? = null,
    onClickItem: ((PhotoInfo) -> (Unit))? = null
) {
    Card(
        modifier = modifier.clickable(enabled = true, onClick = {
            onClickItem?.invoke(info)
        }),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        ItemDetailSlot(
            imageUrl = info.url,
            isLoading = isLoading,
            onIsLoadingChange = onIsLoadingChange
        )
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 480)
@Preview(
    showBackground = true,
    widthDp = 320,
    heightDp = 480,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "GalleryItemPreviewDark"
)
@Composable
fun GalleryItemPreview() {
    ImageGalleryTheme {
        val isLoading = remember { mutableStateOf(true) }

        GalleryItem(
            modifier = Modifier
                .height(150.dp)
                .width(150.dp),
            info = photoItem,
            isLoading = isLoading.value,
            onIsLoadingChange = {
                isLoading.value = it
            }
        )
    }
}