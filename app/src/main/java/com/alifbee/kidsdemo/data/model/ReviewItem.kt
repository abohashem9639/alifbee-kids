package com.alifbee.kidsdemo.data.model

data class ReviewItem(
    val id: Int,
    val text: String,
    val translation: String?,
    val sound: String?,
    val score: Int,
    val isFavorite: Boolean,
    val levelId: Int,
    val lessonId: Int,
    val lessonSlug: String,
)
