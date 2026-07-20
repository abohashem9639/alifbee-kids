package com.alifbee.kidsdemo.data.model

data class Assignment(
    val id: String,
    val title: String,
    val progressPercent: Int,
    val type: String,
    val dueDate: String,
    val classroom: String,
    val lessonsProgress: String,
    val level: String,
    val status: AssignmentStatus,
)

enum class AssignmentStatus { TO_DO, IN_PROGRESS, DONE }
