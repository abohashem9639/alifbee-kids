package com.alifbee.kidsdemo.ui.screens.splash.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEffect
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEvent
import com.alifbee.kidsdemo.ui.common.viewmodel.UiState

internal interface SplashContract {

    sealed interface Event : UiEvent

    sealed interface Effect : UiEffect {
        data object NavigateToHome : Effect
    }

    @Immutable
    data class State(
        val isLoading: Boolean = true,
    ) : UiState
}
