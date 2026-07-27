package com.alifbee.kidsdemo.ui.utils

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.sharedBoundsForNav(key: String): Modifier {
    val sts = LocalSharedTransitionScope.current ?: return this
    val avs = LocalNavAnimatedVisibilityScope.current ?: return this
    return with(sts) {
        this@sharedBoundsForNav.sharedBounds(
            sharedContentState = rememberSharedContentState(key),
            animatedVisibilityScope = avs,
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.sharedElementForNav(key: String): Modifier {
    val sts = LocalSharedTransitionScope.current ?: return this
    val avs = LocalNavAnimatedVisibilityScope.current ?: return this
    return with(sts) {
        this@sharedElementForNav.sharedElement(
            sharedContentState = rememberSharedContentState(key),
            animatedVisibilityScope = avs,
        )
    }
}
