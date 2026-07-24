package com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.alifbee.kidsdemo.data.model.SyllabusCourse
import com.alifbee.kidsdemo.data.model.SyllabusLesson
import com.alifbee.kidsdemo.data.model.SyllabusLevel
import com.alifbee.kidsdemo.data.model.SyllabusTarget
import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEffect
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEvent
import com.alifbee.kidsdemo.ui.common.viewmodel.UiFetchState
import com.alifbee.kidsdemo.ui.common.viewmodel.UiState

internal interface SyllabusContract {

    sealed interface Event : UiEvent {
        data class OnLevelSelected(val levelId: Int) : Event
        data class OnLessonOpened(val lessonId: Int) : Event
        data object OnRetryClicked : Event
    }

    sealed interface Effect : UiEffect

    @Immutable
    data class LevelItem(
        val level: SyllabusLevel,
        val lessonsCount: Int,
        val progress: Float,
    )

    @Immutable
    data class State(
        val course: SyllabusCourse? = null,
        val levels: List<SyllabusLevel> = emptyList(),
        val selectedLevel: SyllabusLevel? = null,
        val lessons: List<SyllabusLesson> = emptyList(),
        val levelItems: List<LevelItem> = emptyList(),
        val completedCount: Int = 0,
        val openedLesson: SyllabusLesson? = null,
        val targets: List<SyllabusTarget> = emptyList(),
        val fetchState: FetchState = FetchState.IsLoading(true),
    ) : UiState

    sealed class FetchState : UiFetchState() {
        data class IsLoading(val isLoading: Boolean) : FetchState()
        data class LoadData(override val error: AppError? = null) : FetchState()
    }
}

internal inline val SyllabusContract.FetchState.loading: Boolean
    get() = if (this is SyllabusContract.FetchState.IsLoading) this.isLoading else false
