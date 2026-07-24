package com.alifbee.kidsdemo.ui.screens.review.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.data.model.ReviewGroup
import com.alifbee.kidsdemo.data.repository.ReviewRepository
import com.alifbee.kidsdemo.data.repository.SyllabusRepository
import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val syllabusRepository: SyllabusRepository,
) : BaseViewModel<ReviewContract.Event, ReviewContract.State, ReviewContract.Effect>(
    initialState = ReviewContract.State()
) {

    init {
        loadReviews()
    }

    override fun handleEvent(event: ReviewContract.Event) {
        when (event) {
            is ReviewContract.Event.OnItemBookmarkClicked -> toggleFavorite(event.item.id, !event.item.isFavorite)
            else -> Unit
        }
    }

    private fun toggleFavorite(id: Int, favorite: Boolean) {
        viewModelScope.launch {
            reviewRepository.setFavorite(id, favorite)
            loadReviews()
        }
    }

    private fun loadReviews() {
        setState { copy(fetchState = ReviewContract.FetchState.IsLoading(true)) }
        viewModelScope.launch {
            val items = reviewRepository.getAllReviews()
            if (items.isEmpty()) {
                setState {
                    copy(
                        fetchState = ReviewContract.FetchState.LoadData(error = AppError.General()),
                    )
                }
                return@launch
            }
            val lessonTitles = items.map { it.lessonId }.distinct().mapNotNull { lessonId ->
                syllabusRepository.getLesson(lessonId)?.let { lesson -> lessonId to lesson.title }
            }.toMap()
            val groups = items.groupBy { it.lessonId }.map { (lessonId, groupItems) ->
                val slug = groupItems.first().lessonSlug
                ReviewGroup(
                    lessonId = lessonId,
                    lessonSlug = slug,
                    title = lessonTitles[lessonId] ?: slug,
                    items = groupItems,
                )
            }
            setState {
                copy(
                    itemsReadyCount = items.size,
                    groups = groups,
                    fetchState = ReviewContract.FetchState.LoadData(),
                )
            }
        }
    }
}
