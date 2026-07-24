package com.alifbee.kidsdemo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class ReviewEntity(
    @PrimaryKey val id: Int,
    val text: String,
    val translation: String?,
    val sound: String?,
    val score: Int,
    val isFavorite: Boolean,
    val levelId: Int,
    val lessonId: Int,
    val lessonSlug: String,
)
