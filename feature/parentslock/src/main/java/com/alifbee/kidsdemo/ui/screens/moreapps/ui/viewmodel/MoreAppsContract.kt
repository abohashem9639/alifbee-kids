package com.alifbee.kidsdemo.ui.screens.moreapps.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.alifbee.kidsdemo.data.model.AppItem
import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEffect
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEvent
import com.alifbee.kidsdemo.ui.common.viewmodel.UiFetchState
import com.alifbee.kidsdemo.ui.common.viewmodel.UiState

internal interface MoreAppsContract {

    sealed interface Event : UiEvent {
        data class OnAppClicked(val app: AppItem) : Event
        data object OnRetryClicked : Event
    }

    sealed interface Effect : UiEffect {
        data class OpenStorePage(val storeUrl: String) : Effect
    }

    @Immutable
    data class State(
        val apps: List<AppItem> = emptyList(),
        val fetchState: FetchState = FetchState.IsLoading(true),
    ) : UiState

    sealed class FetchState : UiFetchState() {
        data class IsLoading(val isLoading: Boolean) : FetchState()
        data class LoadData(override val error: AppError? = null) : FetchState()
    }
}

internal inline val MoreAppsContract.FetchState.loading: Boolean
    get() = if (this is MoreAppsContract.FetchState.IsLoading) this.isLoading else false
