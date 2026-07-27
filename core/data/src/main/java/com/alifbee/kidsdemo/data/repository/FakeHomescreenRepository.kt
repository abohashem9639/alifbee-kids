package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.HomescreenData
import com.alifbee.kidsdemo.data.remote.dto.AssignmentDto
import com.alifbee.kidsdemo.data.remote.dto.AssignmentStatusDto
import com.alifbee.kidsdemo.data.remote.dto.CourseDto
import com.alifbee.kidsdemo.data.remote.dto.DayStateDto
import com.alifbee.kidsdemo.data.remote.dto.DayStatusDto
import com.alifbee.kidsdemo.data.remote.dto.FeatureDto
import com.alifbee.kidsdemo.data.remote.dto.HomescreenDto
import com.alifbee.kidsdemo.data.remote.dto.PricingPlanDto
import com.alifbee.kidsdemo.data.remote.dto.ServiceDto
import com.alifbee.kidsdemo.data.remote.dto.StreakStatsDto
import com.alifbee.kidsdemo.data.remote.dto.UserProfileDto
import com.alifbee.kidsdemo.data.remote.dto.UserStatsDto
import com.alifbee.kidsdemo.data.remote.dto.WeeklyProgressDto
import com.alifbee.kidsdemo.data.remote.mapper.toDomain
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeHomescreenRepository @Inject constructor() : HomescreenRepository {

    override suspend fun getHomescreen(): HomescreenData {
        delay(SIMULATED_NETWORK_DELAY_MS)
        return fakeApiResponse().toDomain()
    }

    private fun fakeApiResponse(): HomescreenDto = HomescreenDto(
        profile = UserProfileDto(
            name = "lora",
            email = "alexmcqueen@gmail.com",
            avatarUrl = null,
            isPremium = true,
        ),
        stats = UserStatsDto(
            honey = 59,
            dailyStreak = 100,
            lessons = 24,
            words = 63,
        ),
        currentCourse = CourseDto(
            id = "arabic-mastery",
            title = "Arabic Mastery",
            level = "A0 - Beginner",
            progressPercent = 40,
        ),
        features = listOf(
            FeatureDto(id = "audio", name = "Audio", icon = "audio"),
            FeatureDto(id = "reading", name = "Reading", icon = "reading"),
            FeatureDto(id = "assessment", name = "Assessment", icon = "assessment"),
            FeatureDto(id = "writing", name = "Writing", icon = "writing"),
            FeatureDto(id = "speaking", name = "Speaking", icon = "speaking"),
            FeatureDto(id = "vocab", name = "Vocab", icon = "vocab"),
            FeatureDto(id = "grammar", name = "Grammar", icon = "grammar"),
            FeatureDto(id = "listening", name = "Listening", icon = "listening"),
            FeatureDto(id = "culture", name = "Culture", icon = "culture"),
            FeatureDto(id = "quiz", name = "Quiz", icon = "quiz"),
        ),
        services = listOf(
            ServiceDto(
                id = "self_assessment",
                name = "Self Assessment",
                icon = "self_assessment",
                backgroundColor = "gray",
            ),
            ServiceDto(
                id = "coaching",
                name = "AlifBee Coaching",
                icon = "coaching",
                backgroundColor = "pink",
            ),
            ServiceDto(
                id = "academy",
                name = "AlifBee Academy",
                icon = "academy",
                backgroundColor = "cream",
            ),
            ServiceDto(
                id = "tutoring",
                name = "Private Tutoring",
                icon = "tutoring",
                backgroundColor = "gray",
            ),
            ServiceDto(
                id = "certification",
                name = "Certification",
                icon = "certification",
                backgroundColor = "pink",
            ),
        ),
        weeklyProgress = WeeklyProgressDto(
            days = listOf(
                DayStatusDto(label = "Mon", state = DayStateDto.COMPLETED, isCurrent = false),
                DayStatusDto(label = "Tue", state = DayStateDto.COMPLETED, isCurrent = false),
                DayStatusDto(label = "Wen", state = DayStateDto.MISSED, isCurrent = false),
                DayStatusDto(label = "Thu", state = DayStateDto.COMPLETED, isCurrent = true),
                DayStatusDto(label = "Fri", state = DayStateDto.PENDING, isCurrent = false),
                DayStatusDto(label = "Sat", state = DayStateDto.PENDING, isCurrent = false),
                DayStatusDto(label = "Sun", state = DayStateDto.PENDING, isCurrent = false),
            ),
        ),
        streakStats = StreakStatsDto(
            currentStreak = 88,
            activeDays = 178,
            longestStreak = 90,
        ),
        currentAssignment = AssignmentDto(
            id = "assignment-1",
            title = "Assignment title 1",
            progressPercent = 13,
            type = "Quiz",
            dueDate = "Dec 9, 2021",
            classroom = "C1",
            lessonsProgress = "2/24",
            level = "Elementary",
            status = AssignmentStatusDto.TO_DO,
        ),
        pricingPlans = listOf(
            PricingPlanDto(
                id = "plan_1m",
                months = 1,
                priceUsd = 8.99,
                originalPriceUsd = null,
                discountPercent = null,
                isBestValue = false,
            ),
            PricingPlanDto(
                id = "plan_12m",
                months = 12,
                priceUsd = 54.0,
                originalPriceUsd = 108.0,
                discountPercent = 50,
                isBestValue = true,
            ),
            PricingPlanDto(
                id = "plan_6m",
                months = 6,
                priceUsd = 36.0,
                originalPriceUsd = 51.40,
                discountPercent = 50,
                isBestValue = false,
            ),
        ),
    )

    companion object {
        private const val SIMULATED_NETWORK_DELAY_MS = 400L
    }
}
