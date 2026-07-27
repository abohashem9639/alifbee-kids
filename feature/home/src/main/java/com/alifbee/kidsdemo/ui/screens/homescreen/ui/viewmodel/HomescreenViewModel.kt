package com.alifbee.kidsdemo.ui.screens.homescreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.data.datastore.DataStoreManager
import com.alifbee.kidsdemo.data.repository.HomescreenRepository
import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomescreenViewModel @Inject constructor(
    private val homescreenRepository: HomescreenRepository,
    private val dataStoreManager: DataStoreManager,
) : BaseViewModel<HomescreenContract.Event, HomescreenContract.State, HomescreenContract.Effect>(
    initialState = HomescreenContract.State()
) {

    init {
        observeUserSession()
        loadHomescreen()
    }

    private fun observeUserSession() {
        viewModelScope.launch {
            dataStoreManager.userSession.collect { session ->
                val current = uiState.value.profile ?: return@collect
                setState {
                    copy(
                        profile = current.copy(
                            name = session?.fullName?.takeIf { it.isNotBlank() } ?: current.name,
                            email = session?.email?.takeIf { it.isNotBlank() } ?: current.email,
                        )
                    )
                }
            }
        }
    }

    override fun handleEvent(event: HomescreenContract.Event) {
        when (event) {
            HomescreenContract.Event.OnSettingsClicked -> setEffect { HomescreenContract.Effect.NavigateToChangeLanguage }
            else -> Unit
        }
    }

    private fun loadHomescreen() {
        setState { copy(fetchState = HomescreenContract.FetchState.IsLoading(true)) }
        viewModelScope.launch {
            try {
                val data = homescreenRepository.getHomescreen()
                val session = dataStoreManager.userSession.first()
                val profile = data.profile.copy(
                    name = session?.fullName?.takeIf { it.isNotBlank() } ?: data.profile.name,
                    email = session?.email?.takeIf { it.isNotBlank() } ?: data.profile.email,
                )
                setState {
                    copy(
                        profile = profile,
                        stats = data.stats,
                        currentCourse = data.currentCourse,
                        features = data.features,
                        services = data.services,
                        weeklyProgress = data.weeklyProgress,
                        streakStats = data.streakStats,
                        currentAssignment = data.currentAssignment,
                        pricingPlans = data.pricingPlans,
                        fetchState = HomescreenContract.FetchState.LoadData(),
                    )
                }
            } catch (e: Exception) {
                setState {
                    copy(fetchState = HomescreenContract.FetchState.LoadData(error = AppError.General(e.message)))
                }
            }
        }
    }
}
