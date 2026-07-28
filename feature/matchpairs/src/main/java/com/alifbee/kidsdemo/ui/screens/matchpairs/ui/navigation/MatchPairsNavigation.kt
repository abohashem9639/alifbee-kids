package com.alifbee.kidsdemo.ui.screens.matchpairs.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.alifbee.kidsdemo.ui.screens.matchpairs.ui.MatchPairsScreen
import com.alifbee.kidsdemo.ui.screens.matchpairs.ui.viewmodel.MatchPairsViewModel
import com.alifbee.kidsdemo.ui.utils.slideComposable
import kotlinx.serialization.Serializable

@Serializable
data object MatchPairsRoute

fun NavGraphBuilder.matchPairsRoute(
    isRtl: Boolean,
    onCloseClicked: () -> Unit,
) {
    slideComposable<MatchPairsRoute>(isRtlDirection = isRtl) {
        val viewModel: MatchPairsViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsState()
        MatchPairsScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
            onCloseClicked = onCloseClicked,
        )
    }
}
