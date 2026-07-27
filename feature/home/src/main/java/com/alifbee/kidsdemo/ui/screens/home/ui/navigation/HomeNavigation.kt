package com.alifbee.kidsdemo.ui.screens.home.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.alifbee.kidsdemo.ui.screens.home.ui.HomeScreen
import com.alifbee.kidsdemo.ui.screens.home.ui.viewmodel.HomeViewModel
import com.alifbee.kidsdemo.ui.utils.fadeComposable
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeRoute(
    onNavigateToHomescreen: () -> Unit,
    onNavigateToParentsLock: () -> Unit,
) {
    fadeComposable<HomeRoute> {
        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsState()
        HomeScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
            onNavigateToHomescreen = onNavigateToHomescreen,
            onNavigateToParentsLock = onNavigateToParentsLock,
        )
    }
}
