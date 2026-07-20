package com.alifbee.kidsdemo.data.model

data class DayStatus(
    val label: String,
    val completed: Boolean,
    val isCurrent: Boolean,
)

data class WeeklyProgress(
    val days: List<DayStatus>,
)
