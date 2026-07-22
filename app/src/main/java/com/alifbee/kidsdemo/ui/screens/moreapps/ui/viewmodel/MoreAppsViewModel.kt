package com.alifbee.kidsdemo.ui.screens.moreapps.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.data.repository.AppsRepository
import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MoreAppsViewModel @Inject constructor(
    private val appsRepository: AppsRepository,
) : BaseViewModel<MoreAppsContract.Event, MoreAppsContract.State, MoreAppsContract.Effect>(
    initialState = MoreAppsContract.State()
) {

    init {
        loadApps()
    }

    override fun handleEvent(event: MoreAppsContract.Event) {
        when (event) {
            is MoreAppsContract.Event.OnAppClicked -> {
                if (event.app.storeUrl.isNotBlank()) {
                    setEffect { MoreAppsContract.Effect.OpenStorePage(event.app.storeUrl) }
                }
            }
            MoreAppsContract.Event.OnRetryClicked -> loadApps()
        }
    }

    private fun loadApps() {
        setState { copy(fetchState = MoreAppsContract.FetchState.IsLoading(true)) }
        viewModelScope.launch {
            try {
                val apps = appsRepository.getApps()
                setState { copy(apps = apps, fetchState = MoreAppsContract.FetchState.LoadData()) }
            } catch (e: Exception) {
                setState {
                    copy(fetchState = MoreAppsContract.FetchState.LoadData(error = AppError.General(e.message)))
                }
            }
        }
    }
}
