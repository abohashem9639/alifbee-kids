package com.alifbee.kidsdemo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SyllabusBody(
    @SerializedName("courses") val courses: List<SyllabusCourseDto>?,
    @SerializedName("levels") val levels: List<SyllabusLevelDto>?,
    @SerializedName("lessons") val lessons: List<SyllabusLessonDto>?,
    @SerializedName("targets") val targets: List<SyllabusTargetDto>?,
)

data class SyllabusCourseDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("slug") val slug: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("order") val order: Int?,
    @SerializedName("levelCount") val levelCount: Int?,
    @SerializedName("isPremium") val isPremium: Boolean?,
)

data class SyllabusLevelDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("slug") val slug: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("order") val order: Int?,
)

data class SyllabusLessonDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("order") val order: Int?,
    @SerializedName("slug") val slug: String?,
    @SerializedName("estimate") val estimate: Int?,
    @SerializedName("isRevise") val isRevise: Boolean?,
    @SerializedName("levelId") val levelId: Int?,
    @SerializedName("status") val status: Int?,
)

data class SyllabusTargetDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("slug") val slug: String?,
    @SerializedName("order") val order: Int?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("unlocked") val unlocked: Int?,
    @SerializedName("lessonId") val lessonId: Int?,
    @SerializedName("levelId") val levelId: Int?,
    @SerializedName("status") val status: Int?,
)
