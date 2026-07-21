package com.alifbee.kidsdemo.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.core.UiState
import com.alifbee.kidsdemo.data.model.ReviewData
import com.alifbee.kidsdemo.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val repository: ReviewRepository,
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<ReviewData>>()
    val uiState: LiveData<UiState<ReviewData>> = _uiState

    init {
        loadReview()
    }

    fun loadReview() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                UiState.Success(repository.getReview())
            } catch (e: Exception) {
                UiState.Error(e.message ?: "Failed to load review")
            }
        }
    }
}
