package com.alifbee.kidsdemo.ui.screens.review.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.alifbee.kidsdemo.ui.common.BottomBarNavRoute
import com.alifbee.kidsdemo.ui.screens.review.ui.ReviewScreen
import com.alifbee.kidsdemo.ui.screens.review.ui.viewmodel.ReviewViewModel
import com.alifbee.kidsdemo.ui.utils.fadeComposable
import kotlinx.serialization.Serializable

@Serializable
data object ReviewRoute : BottomBarNavRoute

fun NavGraphBuilder.reviewRoute() {
    fadeComposable<ReviewRoute> {
        val viewModel: ReviewViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsState()
        ReviewScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
        )
    }
}
