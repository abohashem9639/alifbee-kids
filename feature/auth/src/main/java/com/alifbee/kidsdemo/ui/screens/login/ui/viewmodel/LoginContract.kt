package com.alifbee.kidsdemo.ui.screens.login.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEffect
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEvent
import com.alifbee.kidsdemo.ui.common.viewmodel.UiFetchState
import com.alifbee.kidsdemo.ui.common.viewmodel.UiState

internal interface LoginContract {

    sealed interface Event : UiEvent {
        data class OnLoginClicked(val email: String, val password: String) : Event
    }

    sealed interface Effect : UiEffect {
        data object SignInSuccessNavigate : Effect
    }

    @Immutable
    data class State(
        val fetchState: FetchState = FetchState.LoadData(),
    ) : UiState

    sealed class FetchState : UiFetchState() {
        data class IsLoading(val isLoading: Boolean) : FetchState()
        data class LoadData(override val error: AppError? = null) : FetchState()
    }
}

internal inline val LoginContract.FetchState.loading: Boolean
    get() = if (this is LoginContract.FetchState.IsLoading) this.isLoading else false
