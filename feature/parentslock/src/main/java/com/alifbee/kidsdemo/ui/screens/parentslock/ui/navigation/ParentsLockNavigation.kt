package com.alifbee.kidsdemo.ui.screens.parentslock.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.alifbee.kidsdemo.ui.screens.parentslock.ui.ParentsLockScreen
import com.alifbee.kidsdemo.ui.screens.parentslock.ui.viewmodel.ParentsLockViewModel
import com.alifbee.kidsdemo.ui.utils.slideComposable
import kotlinx.serialization.Serializable

@Serializable
data object ParentsLockRoute

fun NavGraphBuilder.parentsLockRoute(
    isRtl: Boolean,
    onBackClicked: () -> Unit,
    onNavigateToMoreApps: () -> Unit,
) {
    slideComposable<ParentsLockRoute>(isRtlDirection = isRtl) {
        val viewModel: ParentsLockViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsState()
        ParentsLockScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
            onBackClicked = onBackClicked,
            onNavigateToMoreApps = onNavigateToMoreApps,
        )
    }
}
