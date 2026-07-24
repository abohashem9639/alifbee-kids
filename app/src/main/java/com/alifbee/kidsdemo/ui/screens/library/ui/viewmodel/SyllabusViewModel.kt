package com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.data.datastore.DataStoreManager
import com.alifbee.kidsdemo.data.model.Syllabus
import com.alifbee.kidsdemo.data.model.SyllabusLevel
import com.alifbee.kidsdemo.data.repository.SyllabusRepository
import com.alifbee.kidsdemo.data.result.onFailure
import com.alifbee.kidsdemo.data.result.onSuccess
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SyllabusViewModel @Inject constructor(
    private val syllabusRepository: SyllabusRepository,
    private val dataStoreManager: DataStoreManager,
) : BaseViewModel<SyllabusContract.Event, SyllabusContract.State, SyllabusContract.Effect>(
    initialState = SyllabusContract.State()
) {

    private var syllabus: Syllabus? = null
    private var openedLessonId: Int? = null

    init {
        loadSyllabus()
    }

    override fun handleEvent(event: SyllabusContract.Event) {
        when (event) {
            is SyllabusContract.Event.OnLevelSelected -> selectLevel(event.levelId)
            is SyllabusContract.Event.OnLessonOpened -> openLesson(event.lessonId)
            SyllabusContract.Event.OnRetryClicked -> loadSyllabus()
        }
    }

    private fun loadSyllabus() {
        setState { copy(fetchState = SyllabusContract.FetchState.IsLoading(true)) }
        viewModelScope.launch {
            syllabusRepository.getSyllabus()
                .onSuccess { data ->
                    syllabus = data
                    val levels = data.levels.sortedBy { it.order }
                    val savedId = dataStoreManager.selectedLevelId.first()
                    val selected = levels.firstOrNull { it.id == savedId } ?: levels.firstOrNull()
                    setState {
                        copy(
                            course = data.courses.firstOrNull(),
                            levels = levels,
                            levelItems = levelItems(data, levels),
                            fetchState = SyllabusContract.FetchState.LoadData(),
                        )
                    }
                    selected?.let { applyLevel(it) }
                    openedLessonId?.let { applyOpenedLesson(data, it) }
                }
                .onFailure { error ->
                    setState { copy(fetchState = SyllabusContract.FetchState.LoadData(error = error)) }
                }
        }
    }

    private fun selectLevel(levelId: Int) {
        val level = uiState.value.levels.firstOrNull { it.id == levelId } ?: return
        applyLevel(level)
        viewModelScope.launch { dataStoreManager.setSelectedLevelId(levelId) }
    }

    private fun openLesson(lessonId: Int) {
        openedLessonId = lessonId
        syllabus?.let { applyOpenedLesson(it, lessonId) }
    }

    private fun applyOpenedLesson(data: Syllabus, lessonId: Int) {
        val lesson = data.lessons.firstOrNull { it.id == lessonId }
        val targets = data.targets.filter { it.lessonId == lessonId }
        setState { copy(openedLesson = lesson, targets = targets) }
    }

    private fun applyLevel(level: SyllabusLevel) {
        val lessons = syllabus?.lessons
            ?.filter { it.levelId == level.id }
            .orEmpty()
        setState {
            copy(
                selectedLevel = level,
                lessons = lessons,
                completedCount = lessons.count { it.status != 0 },
            )
        }
    }

    private fun levelItems(data: Syllabus, levels: List<SyllabusLevel>): List<SyllabusContract.LevelItem> =
        levels.map { level ->
            val lessons = data.lessons.filter { it.levelId == level.id }
            SyllabusContract.LevelItem(
                level = level,
                lessonsCount = lessons.size,
                progress = if (lessons.isEmpty()) {
                    0f
                } else {
                    lessons.count { it.status != 0 } / lessons.size.toFloat()
                },
            )
        }
}
