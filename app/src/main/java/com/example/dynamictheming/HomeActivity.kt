package com.example.dynamictheming

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.dynamictheming.DummyData.Companion.imageList
import com.example.dynamictheming.util.DynamicThemePrimaryColorsFromImage
import com.example.dynamictheming.util.contrastAgainst
import com.example.dynamictheming.util.rememberDominantColorState
import com.example.dynamictheming.util.verticalGradientScrim
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

/**
 * This is the minimum amount of calculated contrast for a color to be used on top of the
 * surface color. These values are defined within the WCAG AA guidelines, and we use a value of
 * 3:1 which is the minimum for user-interface components.
 */
private const val MinContrastOfPrimaryVsSurface = 3f

@OptIn(ExperimentalPagerApi::class) // HorizontalPager is experimental
@Composable
fun HomeContent(
    imageList: List<ImageData>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val surfaceColor = MaterialTheme.colors.surface
        val dominantColorState = rememberDominantColorState { color ->
            // We want a color which has sufficient contrast against the surface color
            color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
        }

        DynamicThemePrimaryColorsFromImage(dominantColorState) {
            val pagerState = rememberPagerState(
                pageCount = imageList.size,
                initialOffscreenLimit = 2
            )

            val selectedImageUrl = imageList[pagerState.currentPage].image
            LaunchedEffect(selectedImageUrl) {
                dominantColorState.updateColorsFromImageUrl(selectedImageUrl)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalGradientScrim(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.38f),
                        startYPercentage = 1f,
                        endYPercentage = 0f
                    )
            ) {
                ImageContainer(
                    items = imageList,
                    pagerState = pagerState,
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}


@ExperimentalPagerApi // HorizontalPager is experimental
@Composable
fun ImageContainer(
    items: List<ImageData>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        itemSpacing = 4.dp
    ) { page ->
        val imageItem = items[page]


        val isSelected = pagerState.currentPage == page

        ImageListCarouselItem(
            imageUrl = imageItem.image,
            isSelected = isSelected
        )
    }
}

@Composable
private fun ImageListCarouselItem(
    imageUrl: String? = null,
    isSelected: Boolean,
) {

    val transition = updateTransition(isSelected, label = "Image Width Transition")

    val imageWidth by transition.animateDp { isSelected ->
        if (isSelected) 250.dp else 200.dp
    }

    Box(
        Modifier
            .width(imageWidth)

    ) {
        if (imageUrl != null) {
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium),
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun Home() {
    HomeContent(
        imageList = imageList
    )
}
