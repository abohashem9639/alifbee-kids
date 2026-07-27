package com.alifbee.kidsdemo.data.remote.mapper

import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.data.model.Assignment
import com.alifbee.kidsdemo.data.model.AssignmentStatus
import com.alifbee.kidsdemo.data.model.Course
import com.alifbee.kidsdemo.data.model.DayState
import com.alifbee.kidsdemo.data.model.DayStatus
import com.alifbee.kidsdemo.data.model.Feature
import com.alifbee.kidsdemo.data.model.HomescreenData
import com.alifbee.kidsdemo.data.model.PricingPlan
import com.alifbee.kidsdemo.data.model.Service
import com.alifbee.kidsdemo.data.model.StreakStats
import com.alifbee.kidsdemo.data.model.UserProfile
import com.alifbee.kidsdemo.data.model.UserStats
import com.alifbee.kidsdemo.data.model.WeeklyProgress
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

fun HomescreenDto.toDomain(): HomescreenData = HomescreenData(
    profile = profile.toDomain(),
    stats = stats.toDomain(),
    currentCourse = currentCourse?.toDomain(),
    features = features.map { it.toDomain() },
    services = services.map { it.toDomain() },
    weeklyProgress = weeklyProgress.toDomain(),
    streakStats = streakStats.toDomain(),
    currentAssignment = currentAssignment?.toDomain(),
    pricingPlans = pricingPlans.map { it.toDomain() },
)

private fun UserProfileDto.toDomain() =
    UserProfile(name = name, email = email, avatarUrl = avatarUrl, isPremium = isPremium)

private fun UserStatsDto.toDomain() =
    UserStats(honey = honey, dailyStreak = dailyStreak, lessons = lessons, words = words)

private fun CourseDto.toDomain() =
    Course(id = id, title = title, level = level, progressPercent = progressPercent)

private fun FeatureDto.toDomain() =
    Feature(id = id, name = name, iconRes = resolveIcon(icon))

private fun ServiceDto.toDomain() = Service(
    id = id,
    name = name,
    iconRes = resolveIcon(icon),
    backgroundColorRes = resolveServiceBackground(backgroundColor),
)

private fun WeeklyProgressDto.toDomain() =
    WeeklyProgress(days = days.map { it.toDomain() })

private fun DayStatusDto.toDomain() = DayStatus(
    label = label,
    state = when (state) {
        DayStateDto.COMPLETED -> DayState.COMPLETED
        DayStateDto.MISSED -> DayState.MISSED
        DayStateDto.PENDING -> DayState.PENDING
    },
    isCurrent = isCurrent,
)

private fun StreakStatsDto.toDomain() = StreakStats(
    currentStreak = currentStreak,
    activeDays = activeDays,
    longestStreak = longestStreak,
)

private fun AssignmentDto.toDomain() = Assignment(
    id = id,
    title = title,
    progressPercent = progressPercent,
    type = type,
    dueDate = dueDate,
    classroom = classroom,
    lessonsProgress = lessonsProgress,
    level = level,
    status = when (status) {
        AssignmentStatusDto.TO_DO -> AssignmentStatus.TO_DO
        AssignmentStatusDto.DONE -> AssignmentStatus.DONE
        AssignmentStatusDto.OVERDUE -> AssignmentStatus.OVERDUE
    },
)

private fun PricingPlanDto.toDomain() = PricingPlan(
    id = id,
    months = months,
    priceUsd = priceUsd,
    originalPriceUsd = originalPriceUsd,
    discountPercent = discountPercent,
    isBestValue = isBestValue,
)

private fun resolveIcon(name: String): Int = when (name.lowercase()) {
    "audio", "speaking", "listening", "coaching" -> R.drawable.ic_spaking_man
    "reading", "vocab" -> R.drawable.ic_courses
    "assessment", "grammar", "self_assessment" -> R.drawable.ic_notes
    "writing", "tutoring" -> R.drawable.ic_lesson
    "culture", "academy" -> R.drawable.ic_alifbee_academy
    "quiz", "certification" -> R.drawable.ic_medal
    else -> R.drawable.ic_alifbee
}

private fun resolveServiceBackground(color: String): Int = when (color.lowercase()) {
    "pink" -> R.color.service_bg_pink
    "cream" -> R.color.service_bg_cream
    else -> R.color.service_bg_gray
}
