package com.alifbee.kidsdemo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Syllabus(
    val courses: List<SyllabusCourse>,
    val levels: List<SyllabusLevel>,
    val lessons: List<SyllabusLesson>,
    val targets: List<SyllabusTarget>,
)

@Serializable
data class SyllabusCourse(
    val id: Int,
    val slug: String,
    val language: String,
    val icon: String,
    val title: String,
    val description: String,
    val order: Int,
    val levelCount: Int,
    val isPremium: Boolean,
)

@Serializable
data class SyllabusLevel(
    val id: Int,
    val slug: String,
    val title: String,
    val description: String,
    val order: Int,
)

@Serializable
data class SyllabusLesson(
    val id: Int,
    val title: String,
    val icon: String,
    val order: Int,
    val slug: String,
    val estimate: Int,
    val isRevise: Boolean,
    val levelId: Int,
    val status: Int,
)

@Serializable
data class SyllabusTarget(
    val id: Int,
    val title: String,
    val type: String,
    val slug: String,
    val order: Int,
    val icon: String,
    val unlocked: Boolean,
    val lessonId: Int,
    val levelId: Int,
    val status: Int,
)
