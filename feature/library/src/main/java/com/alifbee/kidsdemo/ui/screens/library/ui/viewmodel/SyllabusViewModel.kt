package com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.data.datastore.DataStoreManager
import com.alifbee.kidsdemo.data.model.SyllabusLesson
import com.alifbee.kidsdemo.data.model.SyllabusLevel
import com.alifbee.kidsdemo.data.repository.SyllabusRepository
import com.alifbee.kidsdemo.data.result.AppError
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

    private var openedLessonId: Int? = null

    init {
        loadFromDb()
    }

    override fun handleEvent(event: SyllabusContract.Event) {
        when (event) {
            is SyllabusContract.Event.OnLevelSelected -> selectLevel(event.levelId)
            is SyllabusContract.Event.OnLessonOpened -> openLesson(event.lessonId)
            SyllabusContract.Event.OnRetryClicked -> loadFromDb()
        }
    }

    private fun loadFromDb() {
        setState { copy(fetchState = SyllabusContract.FetchState.IsLoading(true)) }
        viewModelScope.launch {
            val courses = syllabusRepository.getCourses()
            val levels = syllabusRepository.getLevels()
            if (levels.isEmpty()) {
                setState {
                    copy(
                        fetchState = SyllabusContract.FetchState.LoadData(
                            error = AppError.General()
                        )
                    )
                }
                return@launch
            }
            val savedId = dataStoreManager.selectedLevelId.first()
            val selected = levels.firstOrNull { it.id == savedId } ?: levels.first()
            val allLessons = syllabusRepository.getAllLessons()
            setState {
                copy(
                    course = courses.firstOrNull(),
                    levels = levels,
                    levelItems = levelItems(levels, allLessons),
                    fetchState = SyllabusContract.FetchState.LoadData(),
                )
            }
            applyLevel(selected)
            openedLessonId?.let { applyOpenedLesson(it) }
        }
    }

    private fun selectLevel(levelId: Int) {
        val level = uiState.value.levels.firstOrNull { it.id == levelId } ?: return
        viewModelScope.launch {
            dataStoreManager.setSelectedLevelId(levelId)
            applyLevel(level)
        }
    }

    private fun openLesson(lessonId: Int) {
        openedLessonId = lessonId
        viewModelScope.launch { applyOpenedLesson(lessonId) }
    }

    private suspend fun applyOpenedLesson(lessonId: Int) {
        val lesson = syllabusRepository.getLesson(lessonId)
        val targets = syllabusRepository.getTargetsForLesson(lessonId)
        setState { copy(openedLesson = lesson, targets = targets) }
    }

    private suspend fun applyLevel(level: SyllabusLevel) {
        val lessons = syllabusRepository.getLessonsForLevel(level.id)
        setState {
            copy(
                selectedLevel = level,
                lessons = lessons,
                completedCount = lessons.count { it.status != 0 },
            )
        }
    }

    private fun levelItems(
        levels: List<SyllabusLevel>,
        allLessons: List<SyllabusLesson>,
    ): List<SyllabusContract.LevelItem> = levels.map { level ->
        val lessons = allLessons.filter { it.levelId == level.id }
        SyllabusContract.LevelItem(
            level = level,
            lessonsCount = lessons.size,
            progress = if (lessons.isEmpty()) 0f
            else lessons.count { it.status != 0 } / lessons.size.toFloat(),
        )
    }
}
