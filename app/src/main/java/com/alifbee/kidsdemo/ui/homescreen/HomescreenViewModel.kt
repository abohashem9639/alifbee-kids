package com.alifbee.kidsdemo.ui.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.core.UiState
import com.alifbee.kidsdemo.data.model.HomescreenData
import com.alifbee.kidsdemo.data.repository.HomescreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomescreenViewModel @Inject constructor(
    private val repository: HomescreenRepository,
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<HomescreenData>>()
    val uiState: LiveData<UiState<HomescreenData>> = _uiState

    init {
        loadHomescreen()
    }

    fun loadHomescreen() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                UiState.Success(repository.getHomescreen())
            } catch (e: Exception) {
                UiState.Error(e.message ?: "Failed to load homescreen")
            }
        }
    }
}
