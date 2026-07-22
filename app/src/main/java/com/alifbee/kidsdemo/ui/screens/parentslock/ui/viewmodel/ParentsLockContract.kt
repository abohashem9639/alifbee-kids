package com.alifbee.kidsdemo.ui.screens.parentslock.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEffect
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEvent
import com.alifbee.kidsdemo.ui.common.viewmodel.UiState

internal interface ParentsLockContract {

    sealed interface Event : UiEvent {
        data class OnDigitClicked(val digit: Int) : Event
        data object OnDeleteClicked : Event
    }

    sealed interface Effect : UiEffect {
        data object NavigateToMoreApps : Effect
    }

    @Immutable
    data class State(
        val codeWords: String = "",
        val entered: List<Int> = emptyList(),
    ) : UiState
}
