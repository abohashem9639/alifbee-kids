package com.alifbee.kidsdemo.ui.screens.splash.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.alifbee.kidsdemo.ui.screens.splash.ui.SplashScreen
import com.alifbee.kidsdemo.ui.screens.splash.ui.viewmodel.SplashViewModel
import com.alifbee.kidsdemo.ui.utils.fadeComposable
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute

fun NavGraphBuilder.splashRoute(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    fadeComposable<SplashRoute> {
        val viewModel: SplashViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsState()
        SplashScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
            onNavigateToHome = onNavigateToHome,
            onNavigateToLogin = onNavigateToLogin,
        )
    }
}
