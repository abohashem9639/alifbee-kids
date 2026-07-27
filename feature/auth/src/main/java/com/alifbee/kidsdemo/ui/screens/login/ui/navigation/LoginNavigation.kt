package com.alifbee.kidsdemo.ui.screens.login.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.alifbee.kidsdemo.ui.screens.login.ui.LoginScreen
import com.alifbee.kidsdemo.ui.screens.login.ui.viewmodel.LoginViewModel
import com.alifbee.kidsdemo.ui.utils.slideComposable
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

fun NavGraphBuilder.loginRoute(
    isRtl: Boolean,
    onCloseClicked: () -> Unit,
    onSignInSuccessNavigate: () -> Unit,
) {
    slideComposable<LoginRoute>(isRtlDirection = isRtl) {
        val viewModel: LoginViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsState()
        LoginScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
            onCloseClicked = onCloseClicked,
            onSignInSuccessNavigate = onSignInSuccessNavigate,
        )
    }
}
