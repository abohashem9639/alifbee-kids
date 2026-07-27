package com.alifbee.kidsdemo.ui.utils

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ROUTE_ANIM_DURATION_MS = 300

inline fun <reified T : Any> NavGraphBuilder.slideComposable(
    isRtlDirection: Boolean,
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    val direction = if (isRtlDirection) -1 else 1
    composable<T>(
        enterTransition = {
            slideInHorizontally(tween(ROUTE_ANIM_DURATION_MS, easing = FastOutSlowInEasing)) { it * direction }
        },
        exitTransition = {
            slideOutHorizontally(tween(ROUTE_ANIM_DURATION_MS, easing = FastOutSlowInEasing)) { -it * direction }
        },
        popEnterTransition = {
            slideInHorizontally(tween(ROUTE_ANIM_DURATION_MS, easing = FastOutSlowInEasing)) { -it * direction }
        },
        popExitTransition = {
            slideOutHorizontally(tween(ROUTE_ANIM_DURATION_MS, easing = FastOutSlowInEasing)) { it * direction }
        },
    ) { backStackEntry ->
        val animScope = this
        CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides animScope) {
            with(animScope) { content(backStackEntry) }
        }
    }
}

inline fun <reified T : Any> NavGraphBuilder.fadeComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = { fadeIn(tween(ROUTE_ANIM_DURATION_MS, easing = FastOutSlowInEasing)) },
        exitTransition = { fadeOut(tween(ROUTE_ANIM_DURATION_MS, easing = FastOutSlowInEasing)) },
        popEnterTransition = { fadeIn(tween(ROUTE_ANIM_DURATION_MS, easing = FastOutSlowInEasing)) },
        popExitTransition = { fadeOut(tween(ROUTE_ANIM_DURATION_MS, easing = FastOutSlowInEasing)) },
    ) { backStackEntry ->
        val animScope = this
        CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides animScope) {
            with(animScope) { content(backStackEntry) }
        }
    }
}
