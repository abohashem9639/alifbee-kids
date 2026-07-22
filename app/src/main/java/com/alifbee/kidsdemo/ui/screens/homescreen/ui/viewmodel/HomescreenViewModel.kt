package com.alifbee.kidsdemo.ui.screens.homescreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.data.repository.HomescreenRepository
import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomescreenViewModel @Inject constructor(
    private val homescreenRepository: HomescreenRepository,
) : BaseViewModel<HomescreenContract.Event, HomescreenContract.State, HomescreenContract.Effect>(
    initialState = HomescreenContract.State()
) {

    init {
        loadHomescreen()
    }

    override fun handleEvent(event: HomescreenContract.Event) = Unit

    private fun loadHomescreen() {
        setState { copy(fetchState = HomescreenContract.FetchState.IsLoading(true)) }
        viewModelScope.launch {
            try {
                val data = homescreenRepository.getHomescreen()
                setState {
                    copy(
                        profile = data.profile,
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
