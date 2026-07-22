package com.alifbee.kidsdemo.ui.screens.review.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.data.repository.ReviewRepository
import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
) : BaseViewModel<ReviewContract.Event, ReviewContract.State, ReviewContract.Effect>(
    initialState = ReviewContract.State()
) {

    init {
        loadReview()
    }

    override fun handleEvent(event: ReviewContract.Event) = Unit

    private fun loadReview() {
        setState { copy(fetchState = ReviewContract.FetchState.IsLoading(true)) }
        viewModelScope.launch {
            try {
                val data = reviewRepository.getReview()
                setState {
                    copy(
                        itemsReadyCount = data.itemsReadyCount,
                        groups = data.groups,
                        fetchState = ReviewContract.FetchState.LoadData(),
                    )
                }
            } catch (e: Exception) {
                setState {
                    copy(fetchState = ReviewContract.FetchState.LoadData(error = AppError.General(e.message)))
                }
            }
        }
    }
}
