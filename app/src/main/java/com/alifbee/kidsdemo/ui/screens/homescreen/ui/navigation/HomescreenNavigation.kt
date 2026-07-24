package com.alifbee.kidsdemo.ui.screens.homescreen.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.alifbee.kidsdemo.ui.common.BottomBarNavRoute
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.HomescreenScreen
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.viewmodel.HomescreenViewModel
import com.alifbee.kidsdemo.ui.utils.fadeComposable
import kotlinx.serialization.Serializable

@Serializable
data object HomescreenRoute : BottomBarNavRoute

internal fun NavGraphBuilder.homescreenRoute(
    onNavigateToLogin: () -> Unit,
) {
    fadeComposable<HomescreenRoute> {
        val viewModel: HomescreenViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsState()
        HomescreenScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
            onNavigateToLogin = onNavigateToLogin,
        )
    }
}
