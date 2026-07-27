package com.alifbee.kidsdemo.data.model

data class ReviewGroup(
    val lessonId: Int,
    val lessonSlug: String,
    val title: String,
    val items: List<ReviewItem>,
)
