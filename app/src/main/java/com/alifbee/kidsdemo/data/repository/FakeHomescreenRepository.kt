package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.Assignment
import com.alifbee.kidsdemo.data.model.AssignmentStatus
import com.alifbee.kidsdemo.data.model.Course
import com.alifbee.kidsdemo.data.model.DayStatus
import com.alifbee.kidsdemo.data.model.Feature
import com.alifbee.kidsdemo.data.model.HomescreenData
import com.alifbee.kidsdemo.data.model.PricingPlan
import com.alifbee.kidsdemo.data.model.Service
import com.alifbee.kidsdemo.data.model.StreakStats
import com.alifbee.kidsdemo.data.model.UserProfile
import com.alifbee.kidsdemo.data.model.UserStats
import com.alifbee.kidsdemo.data.model.WeeklyProgress
import com.alifbee.kidsdemo.R
import javax.inject.Inject

class FakeHomescreenRepository @Inject constructor() : HomescreenRepository {

    override suspend fun getHomescreen(): HomescreenData = HomescreenData(
        profile = UserProfile(
            name = "lora",
            email = "alexmcqueen@gmail.com",
            avatarUrl = null,
            isPremium = true,
        ),
        stats = UserStats(
            honey = 59,
            dailyStreak = 2,
            lessons = 2,
            words = 2,
        ),
        currentCourse = Course(
            id = "arabic-mastery",
            title = "Arabic Mastery",
            level = "A0 - Beginner",
            progressPercent = 40,
        ),
        features = listOf(
            Feature(id = "audio", name = "Audio", iconRes = R.drawable.ic_spaking_man),
            Feature(id = "reading", name = "Reading", iconRes = R.drawable.ic_courses),
            Feature(id = "assessment", name = "Assessment", iconRes = R.drawable.ic_notes),
        ),
        services = listOf(
            Service(
                id = "self_assessment",
                name = "Self Assessment",
                iconRes = R.drawable.ic_notes,
                backgroundColorRes = R.color.service_bg_gray,
            ),
            Service(
                id = "coaching",
                name = "AlifBee Coaching",
                iconRes = R.drawable.ic_spaking_man,
                backgroundColorRes = R.color.service_bg_pink,
            ),
            Service(
                id = "academy",
                name = "AlifBee Academy",
                iconRes = R.drawable.ic_alifbee_academy,
                backgroundColorRes = R.color.service_bg_cream,
            ),
        ),
        weeklyProgress = WeeklyProgress(
            days = listOf(
                DayStatus(label = "Mon", completed = true, isCurrent = false),
                DayStatus(label = "Tue", completed = true, isCurrent = false),
                DayStatus(label = "Wen", completed = true, isCurrent = false),
                DayStatus(label = "Thu", completed = true, isCurrent = true),
                DayStatus(label = "Fri", completed = false, isCurrent = false),
                DayStatus(label = "Sat", completed = false, isCurrent = false),
                DayStatus(label = "Sun", completed = false, isCurrent = false),
            ),
        ),
        streakStats = StreakStats(
            currentStreak = 88,
            activeDays = 178,
            longestStreak = 90,
        ),
        currentAssignment = Assignment(
            id = "assignment-1",
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
            PricingPlan(
                id = "plan_1m",
                months = 1,
                priceUsd = 8.99,
                originalPriceUsd = null,
                discountPercent = null,
                isBestValue = false,
            ),
            PricingPlan(
                id = "plan_12m",
                months = 12,
                priceUsd = 54.0,
                originalPriceUsd = 108.0,
                discountPercent = 50,
                isBestValue = true,
            ),
            PricingPlan(
                id = "plan_6m",
                months = 6,
                priceUsd = 36.0,
                originalPriceUsd = 51.40,
                discountPercent = 50,
                isBestValue = false,
            ),
        ),
    )
}
