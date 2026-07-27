package com.alifbee.kidsdemo.ui.screens.home.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.data.model.Category
import kotlinx.coroutines.launch

@Composable
internal fun CategoryCarousel(
    categories: List<Category>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { categories.size })
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(categories[page].imageRes),
                    contentDescription = stringResource(R.string.cd_illustration),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(width = 327.dp, height = 264.dp),
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .width(519.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CarouselArrow(
                iconRes = R.drawable.ic_left_arrow,
                visible = pagerState.currentPage > 0,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                },
            )
            CarouselArrow(
                iconRes = R.drawable.ic_right_arrow,
                visible = pagerState.currentPage < categories.lastIndex,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                },
            )
        }
    }
}

@Composable
private fun CarouselArrow(
    @DrawableRes iconRes: Int,
    visible: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.85f else 1f,
        animationSpec = tween(120),
    )

    Image(
        painter = painterResource(iconRes),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(width = 56.dp, height = 80.dp)
            .alpha(if (visible) 1f else 0f)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = visible,
                onClick = onClick,
            ),
    )
}
