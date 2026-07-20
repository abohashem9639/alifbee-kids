package com.alifbee.kidsdemo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HomescreenDto(
    @SerializedName("profile") val profile: UserProfileDto,
    @SerializedName("stats") val stats: UserStatsDto,
    @SerializedName("current_course") val currentCourse: CourseDto?,
    @SerializedName("features") val features: List<FeatureDto>,
    @SerializedName("services") val services: List<ServiceDto>,
    @SerializedName("weekly_progress") val weeklyProgress: WeeklyProgressDto,
    @SerializedName("streak_stats") val streakStats: StreakStatsDto,
    @SerializedName("current_assignment") val currentAssignment: AssignmentDto?,
    @SerializedName("pricing_plans") val pricingPlans: List<PricingPlanDto>,
)

data class UserProfileDto(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("is_premium") val isPremium: Boolean,
)

data class UserStatsDto(
    @SerializedName("honey") val honey: Int,
    @SerializedName("daily_streak") val dailyStreak: Int,
    @SerializedName("lessons") val lessons: Int,
    @SerializedName("words") val words: Int,
)

data class CourseDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("level") val level: String,
    @SerializedName("progress_percent") val progressPercent: Int,
)

data class FeatureDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("icon") val icon: String,
)

data class ServiceDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("background_color") val backgroundColor: String,
)

data class WeeklyProgressDto(
    @SerializedName("days") val days: List<DayStatusDto>,
)

data class DayStatusDto(
    @SerializedName("label") val label: String,
    @SerializedName("state") val state: DayStateDto,
    @SerializedName("is_current") val isCurrent: Boolean,
)

enum class DayStateDto {
    @SerializedName("completed") COMPLETED,
    @SerializedName("missed") MISSED,
    @SerializedName("pending") PENDING,
}

data class StreakStatsDto(
    @SerializedName("current_streak") val currentStreak: Int,
    @SerializedName("active_days") val activeDays: Int,
    @SerializedName("longest_streak") val longestStreak: Int,
)

data class AssignmentDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("progress_percent") val progressPercent: Int,
    @SerializedName("type") val type: String,
    @SerializedName("due_date") val dueDate: String,
    @SerializedName("classroom") val classroom: String,
    @SerializedName("lessons_progress") val lessonsProgress: String,
    @SerializedName("level") val level: String,
    @SerializedName("status") val status: AssignmentStatusDto,
)

enum class AssignmentStatusDto {
    @SerializedName("to_do") TO_DO,
    @SerializedName("done") DONE,
    @SerializedName("overdue") OVERDUE,
}

data class PricingPlanDto(
    @SerializedName("id") val id: String,
    @SerializedName("months") val months: Int,
    @SerializedName("price_usd") val priceUsd: Double,
    @SerializedName("original_price_usd") val originalPriceUsd: Double?,
    @SerializedName("discount_percent") val discountPercent: Int?,
    @SerializedName("is_best_value") val isBestValue: Boolean,
)
