package com.codescreen.image_gallery.ui.gallery_list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.compose.LazyPagingItems
import com.codescreen.image_gallery.domain.model.PhotoInfo
import com.codescreen.image_gallery.ui.theme.ImageGalleryTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun GalleryListScreen(
    items: LazyPagingItems<PhotoInfo>,
    state: LazyGridState = rememberLazyGridState(),
    isLoading: Boolean,
    onItemSelected: ((PhotoInfo) -> (Unit))? = null,
    onEnd: (() -> (Unit))? = null
) {
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.visibleItemsInfo.lastOrNull() }.distinctUntilChanged()
            .filter { visibleItem ->
                visibleItem != null && visibleItem.index == state.layoutInfo.totalItemsCount - 1
            }.collect {
                onEnd?.invoke()
            }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
    ) {
        val (listRef, loadingRef) = createRefs()

        LazyVerticalGrid(
            modifier = Modifier.constrainAs(listRef) {
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(loadingRef.top)
            },
            state = state,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(count = items.itemCount) { index ->
                val isLoading = remember {
                    mutableStateOf(true)
                }

                items[index]?.let {
                    GalleryItem(modifier = Modifier
                        .height(150.dp)
                        .width(150.dp),
                        info = it,
                        isLoading = isLoading.value,
                        onIsLoadingChange = { newValue ->
                            isLoading.value = newValue
                        },
                        onClickItem = { selected ->
                            onItemSelected?.invoke(selected)
                        })
                }
            }
        }

        if (isLoading) {
            LoadingItem(
                modifier = Modifier
                    .constrainAs(loadingRef) {
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable
fun GalleryListScreenPreview() {
    ImageGalleryTheme {
//        GalleryListScreen(
//            items = , isLoading = true
//        )
    }
}