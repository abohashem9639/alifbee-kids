package com.alifbee.kidsdemo.ui.screens.home.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.alifbee.kidsdemo.data.model.Category
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEffect
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEvent
import com.alifbee.kidsdemo.ui.common.viewmodel.UiState

internal interface HomeContract {

    sealed interface Event : UiEvent {
        data object OnMusicClicked : Event
        data object OnMoreByUsClicked : Event
    }

    sealed interface Effect : UiEffect {
        data object NavigateToHomescreen : Effect
        data object NavigateToParentsLock : Effect
    }

    @Immutable
    data class State(
        val categories: List<Category> = emptyList(),
        val isMusicOn: Boolean = true,
    ) : UiState
}
