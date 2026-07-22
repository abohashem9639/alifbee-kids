package com.alifbee.kidsdemo.ui.screens.review.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.alifbee.kidsdemo.data.model.ReviewGroup
import com.alifbee.kidsdemo.data.model.ReviewItem
import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEffect
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEvent
import com.alifbee.kidsdemo.ui.common.viewmodel.UiFetchState
import com.alifbee.kidsdemo.ui.common.viewmodel.UiState

internal interface ReviewContract {

    sealed interface Event : UiEvent {
        data object OnReviewAllClicked : Event
        data object OnInfoClicked : Event
        data class OnItemClicked(val item: ReviewItem) : Event
        data class OnItemAudioClicked(val item: ReviewItem) : Event
        data class OnItemBookmarkClicked(val item: ReviewItem) : Event
    }

    sealed interface Effect : UiEffect

    @Immutable
    data class State(
        val itemsReadyCount: Int = 0,
        val groups: List<ReviewGroup> = emptyList(),
        val fetchState: FetchState = FetchState.IsLoading(true),
    ) : UiState

    sealed class FetchState : UiFetchState() {
        data class IsLoading(val isLoading: Boolean) : FetchState()
        data class LoadData(override val error: AppError? = null) : FetchState()
    }
}

internal inline val ReviewContract.FetchState.loading: Boolean
    get() = if (this is ReviewContract.FetchState.IsLoading) this.isLoading else false
