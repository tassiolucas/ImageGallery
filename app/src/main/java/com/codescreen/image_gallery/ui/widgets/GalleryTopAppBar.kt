package com.codescreen.image_gallery.ui.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codescreen.image_gallery.ui.theme.ImageGalleryTheme

@Composable
fun GalleryTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    showGoBack: Boolean = false,
    onGoBackClick: (() -> (Unit))? = null
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Box {
                Text(
                    modifier = Modifier.fillMaxWidth(0.75F),
                    text = title ?: "Cuties Gallery",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontFamily = FontFamily.SansSerif),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            if (showGoBack) {
                IconButton(
                    onClick = {
                        onGoBackClick?.invoke()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Go Back"
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable
fun GalleryTopAppBarPreview() {
    ImageGalleryTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                GalleryTopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    title = "Gallery Top AppBar",
                    showGoBack = true
                )
            },
        ) {

        }
    }
}