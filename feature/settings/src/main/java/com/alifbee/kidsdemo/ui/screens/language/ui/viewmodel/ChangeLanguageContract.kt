package com.alifbee.kidsdemo.ui.screens.language.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.alifbee.kidsdemo.data.model.AppLanguage
import com.alifbee.kidsdemo.data.model.ThemeMode
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEffect
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEvent
import com.alifbee.kidsdemo.ui.common.viewmodel.UiState

internal interface ChangeLanguageContract {

    sealed interface Event : UiEvent {
        data class OnLanguageSelected(val code: String) : Event
        data class OnThemeSelected(val mode: ThemeMode) : Event
        data object OnDoneClicked : Event
    }

    sealed interface Effect : UiEffect {
        data object NavigateBack : Effect
    }

    @Immutable
    data class State(
        val languages: List<AppLanguage> = emptyList(),
        val selectedCode: String = "",
        val selectedTheme: ThemeMode = ThemeMode.LIGHT,
        val isUpdating: Boolean = false,
    ) : UiState
}
