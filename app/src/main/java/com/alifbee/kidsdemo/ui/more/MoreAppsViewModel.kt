package com.alifbee.kidsdemo.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.core.UiState
import com.alifbee.kidsdemo.data.model.AppItem
import com.alifbee.kidsdemo.data.repository.AppsRepository
import com.alifbee.kidsdemo.data.repository.FakeAppsRepository
import kotlinx.coroutines.launch

class MoreAppsViewModel : ViewModel() {

    private val repository: AppsRepository = FakeAppsRepository()

    private val _uiState = MutableLiveData<UiState<List<AppItem>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<AppItem>>> = _uiState

    init {
        loadApps()
    }

    fun loadApps() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                UiState.Success(repository.getApps())
            } catch (e: Exception) {
                UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
