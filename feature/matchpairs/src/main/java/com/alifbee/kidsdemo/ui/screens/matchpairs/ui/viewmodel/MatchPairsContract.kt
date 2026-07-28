package com.alifbee.kidsdemo.ui.screens.matchpairs.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.alifbee.kidsdemo.data.model.MatchAnswer
import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEffect
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEvent
import com.alifbee.kidsdemo.ui.common.viewmodel.UiFetchState
import com.alifbee.kidsdemo.ui.common.viewmodel.UiState

internal interface MatchPairsContract {

    sealed interface Event : UiEvent {
        data class OnCardDropped(val cardId: Int, val slotIndex: Int?) : Event
        data object OnCheckClicked : Event
    }

    sealed interface Effect : UiEffect

    @Immutable
    data class State(
        val questionText: String = "",
        val cardsA: List<MatchAnswer> = emptyList(),
        val slotsB: List<MatchAnswer> = emptyList(),
        val assignments: Map<Int, Int> = emptyMap(),
        val result: Map<Int, Boolean>? = null,
        val fetchState: FetchState = FetchState.IsLoading(true),
    ) : UiState {
        val allPlaced: Boolean get() = slotsB.isNotEmpty() && assignments.size == slotsB.size
        val solved: Boolean get() = result?.values?.all { it } == true
    }

    sealed class FetchState : UiFetchState() {
        data class IsLoading(val isLoading: Boolean) : FetchState()
        data class LoadData(override val error: AppError? = null) : FetchState()
    }
}

internal inline val MatchPairsContract.FetchState.loading: Boolean
    get() = if (this is MatchPairsContract.FetchState.IsLoading) this.isLoading else false
