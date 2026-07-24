package com.alifbee.kidsdemo.ui.screens.homescreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.alifbee.kidsdemo.data.model.Assignment
import com.alifbee.kidsdemo.data.model.Course
import com.alifbee.kidsdemo.data.model.Feature
import com.alifbee.kidsdemo.data.model.PricingPlan
import com.alifbee.kidsdemo.data.model.Service
import com.alifbee.kidsdemo.data.model.StreakStats
import com.alifbee.kidsdemo.data.model.UserProfile
import com.alifbee.kidsdemo.data.model.UserStats
import com.alifbee.kidsdemo.data.model.WeeklyProgress
import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEffect
import com.alifbee.kidsdemo.ui.common.viewmodel.UiEvent
import com.alifbee.kidsdemo.ui.common.viewmodel.UiFetchState
import com.alifbee.kidsdemo.ui.common.viewmodel.UiState

internal interface HomescreenContract {

    sealed interface Event : UiEvent {
        data object OnSettingsClicked : Event
        data object OnProfileClicked : Event
        data object OnGoPremiumClicked : Event
        data object OnCurrentCourseClicked : Event
        data object OnSeeAllCoursesClicked : Event
        data object OnAssignmentClicked : Event
        data object OnAssignmentStatusClicked : Event
    }

    sealed interface Effect : UiEffect {
        data object NavigateToLogin : Effect
    }

    @Immutable
    data class State(
        val profile: UserProfile? = null,
        val stats: UserStats? = null,
        val currentCourse: Course? = null,
        val features: List<Feature> = emptyList(),
        val services: List<Service> = emptyList(),
        val weeklyProgress: WeeklyProgress? = null,
        val streakStats: StreakStats? = null,
        val currentAssignment: Assignment? = null,
        val pricingPlans: List<PricingPlan> = emptyList(),
        val fetchState: FetchState = FetchState.IsLoading(true),
    ) : UiState

    sealed class FetchState : UiFetchState() {
        data class IsLoading(val isLoading: Boolean) : FetchState()
        data class LoadData(override val error: AppError? = null) : FetchState()
    }
}

internal inline val HomescreenContract.FetchState.loading: Boolean
    get() = if (this is HomescreenContract.FetchState.IsLoading) this.isLoading else false
