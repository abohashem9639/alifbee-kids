package com.alifbee.kidsdemo.ui.screens.homescreen.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.Assignment
import com.alifbee.kidsdemo.data.model.AssignmentStatus
import com.alifbee.kidsdemo.data.model.Course
import com.alifbee.kidsdemo.data.model.DayState
import com.alifbee.kidsdemo.data.model.DayStatus
import com.alifbee.kidsdemo.data.model.Feature
import com.alifbee.kidsdemo.data.model.PricingPlan
import com.alifbee.kidsdemo.data.model.Service
import com.alifbee.kidsdemo.data.model.StreakStats
import com.alifbee.kidsdemo.data.model.UserProfile
import com.alifbee.kidsdemo.data.model.UserStats
import com.alifbee.kidsdemo.data.model.WeeklyProgress
import com.alifbee.kidsdemo.ui.common.CollectEffect
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.components.AssignmentSection
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.components.CurrentCourseSection
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.components.FeaturesSection
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.components.GoPremiumCard
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.components.HomescreenSkeleton
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.components.HomescreenTopBar
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.components.PricingSection
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.components.ProfileCard
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.components.ServicesSection
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.components.StatisticSection
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.viewmodel.HomescreenContract
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.viewmodel.loading
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
internal fun HomescreenScreen(
    state: HomescreenContract.State,
    effectFlow: Flow<HomescreenContract.Effect>,
    onEventSent: (event: HomescreenContract.Event) -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    effectFlow.CollectEffect { effect ->
        when (effect) {
            HomescreenContract.Effect.NavigateToLogin -> onNavigateToLogin()
        }
    }

    HomescreenContent(
        state = state,
        onEventSent = onEventSent,
    )
}

@Composable
private fun HomescreenContent(
    state: HomescreenContract.State,
    onEventSent: (event: HomescreenContract.Event) -> Unit,
) {
    Crossfade(
        targetState = state.fetchState.loading,
        animationSpec = tween(300),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) { isLoading ->
        if (isLoading) {
            HomescreenSkeleton()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .systemBarsPadding()
                    .padding(bottom = 32.dp),
            ) {
                HomescreenTopBar(
                    onSettingsClicked = { onEventSent(HomescreenContract.Event.OnSettingsClicked) },
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
                state.profile?.let { profile ->
                    state.stats?.let { stats ->
                        ProfileCard(
                            profile = profile,
                            stats = stats,
                            onProfileClicked = { onEventSent(HomescreenContract.Event.OnProfileClicked) },
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    }
                }
                GoPremiumCard(
                    onClick = { onEventSent(HomescreenContract.Event.OnGoPremiumClicked) },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp),
                )
                state.currentCourse?.let { course ->
                    CurrentCourseSection(
                        course = course,
                        onCourseClicked = { onEventSent(HomescreenContract.Event.OnCurrentCourseClicked) },
                        onSeeAllClicked = { onEventSent(HomescreenContract.Event.OnSeeAllCoursesClicked) },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 24.dp),
                    )
                }
                FeaturesSection(
                    features = state.features,
                    modifier = Modifier.padding(top = 24.dp),
                )
                ServicesSection(
                    services = state.services,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp),
                )
                state.weeklyProgress?.let { weeklyProgress ->
                    state.streakStats?.let { streakStats ->
                        StatisticSection(
                            weeklyProgress = weeklyProgress,
                            streakStats = streakStats,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(top = 24.dp),
                        )
                    }
                }
                state.currentAssignment?.let { assignment ->
                    AssignmentSection(
                        assignment = assignment,
                        onAssignmentClicked = { onEventSent(HomescreenContract.Event.OnAssignmentClicked) },
                        onAssignmentStatusClicked = { onEventSent(HomescreenContract.Event.OnAssignmentStatusClicked) },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 24.dp),
                    )
                }
                if (state.pricingPlans.isNotEmpty()) {
                    PricingSection(
                        plans = state.pricingPlans,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 32.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomescreenScreenPreview() {
    KidsDemoTheme {
        HomescreenScreen(
            state = HomescreenContract.State(
                profile = UserProfile(
                    name = "lora",
                    email = "alexmcqueen@gmail.com",
                    avatarUrl = null,
                    isPremium = true,
                ),
                stats = UserStats(
                    honey = 59,
                    dailyStreak = 3,
                    lessons = 12,
                    words = 48,
                ),
                currentCourse = Course(
                    id = "1",
                    title = "Arabic Mastery",
                    level = "A0 - Beginner",
                    progressPercent = 40,
                ),
                features = listOf(
                    Feature(id = "1", name = "Audio", iconRes = R.drawable.ic_spaking_man),
                    Feature(id = "2", name = "Videos", iconRes = R.drawable.ic_spaking_man),
                    Feature(id = "3", name = "Games", iconRes = R.drawable.ic_spaking_man),
                ),
                services = listOf(
                    Service(id = "1", name = "Notes", iconRes = R.drawable.ic_notes, backgroundColorRes = R.color.service_bg_gray),
                    Service(id = "2", name = "Self Assessment", iconRes = R.drawable.ic_notes, backgroundColorRes = R.color.service_bg_pink),
                    Service(id = "3", name = "Vocabulary", iconRes = R.drawable.ic_notes, backgroundColorRes = R.color.service_bg_cream),
                ),
                weeklyProgress = WeeklyProgress(
                    days = listOf(
                        DayStatus(label = "Mon", state = DayState.COMPLETED, isCurrent = false),
                        DayStatus(label = "Tue", state = DayState.COMPLETED, isCurrent = false),
                        DayStatus(label = "Wed", state = DayState.MISSED, isCurrent = false),
                        DayStatus(label = "Thu", state = DayState.COMPLETED, isCurrent = true),
                        DayStatus(label = "Fri", state = DayState.PENDING, isCurrent = false),
                        DayStatus(label = "Sat", state = DayState.PENDING, isCurrent = false),
                        DayStatus(label = "Sun", state = DayState.PENDING, isCurrent = false),
                    ),
                ),
                streakStats = StreakStats(
                    currentStreak = 88,
                    activeDays = 178,
                    longestStreak = 90,
                ),
                currentAssignment = Assignment(
                    id = "1",
                    title = "Assignment title 1",
                    progressPercent = 60,
                    type = "Quiz",
                    dueDate = "Dec 9, 2021",
                    classroom = "C1",
                    lessonsProgress = "2/24",
                    level = "Elementary",
                    status = AssignmentStatus.TO_DO,
                ),
                pricingPlans = listOf(
                    PricingPlan(id = "1", months = 1, priceUsd = 9.0, originalPriceUsd = null, discountPercent = null, isBestValue = false),
                    PricingPlan(id = "2", months = 6, priceUsd = 36.0, originalPriceUsd = 54.0, discountPercent = 33, isBestValue = false),
                    PricingPlan(id = "3", months = 12, priceUsd = 54.0, originalPriceUsd = 108.0, discountPercent = 50, isBestValue = true),
                ),
                fetchState = HomescreenContract.FetchState.LoadData(),
            ),
            effectFlow = emptyFlow(),
            onEventSent = {},
            onNavigateToLogin = {},
        )
    }
}
