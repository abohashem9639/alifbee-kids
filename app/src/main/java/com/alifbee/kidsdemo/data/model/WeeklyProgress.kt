package com.alifbee.kidsdemo.data.model

enum class DayState { COMPLETED, MISSED, PENDING }

data class DayStatus(
    val label: String,
    val state: DayState,
    val isCurrent: Boolean,
)

data class WeeklyProgress(
    val days: List<DayStatus>,
)
