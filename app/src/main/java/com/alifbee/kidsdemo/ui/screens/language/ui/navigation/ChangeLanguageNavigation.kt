package com.alifbee.kidsdemo.ui.screens.language.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.alifbee.kidsdemo.ui.screens.language.ui.ChangeLanguageScreen
import com.alifbee.kidsdemo.ui.screens.language.ui.viewmodel.ChangeLanguageViewModel
import com.alifbee.kidsdemo.ui.utils.slideComposable
import kotlinx.serialization.Serializable

@Serializable
data object ChangeLanguageRoute

internal fun NavGraphBuilder.changeLanguageRoute(
    isRtl: Boolean,
    onCloseClicked: () -> Unit,
    onDone: () -> Unit,
) {
    slideComposable<ChangeLanguageRoute>(isRtlDirection = isRtl) {
        val viewModel: ChangeLanguageViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsState()
        ChangeLanguageScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
            onCloseClicked = onCloseClicked,
            onDone = onDone,
        )
    }
}
