package com.alifbee.kidsdemo.data.model

data class HomescreenData(
    val profile: UserProfile,
    val stats: UserStats,
    val currentCourse: Course?,
    val features: List<Feature>,
    val services: List<Service>,
    val weeklyProgress: WeeklyProgress,
    val streakStats: StreakStats,
    val currentAssignment: Assignment?,
    val pricingPlans: List<PricingPlan>,
)
