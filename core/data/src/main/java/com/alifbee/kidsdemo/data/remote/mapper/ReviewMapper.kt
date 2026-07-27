package com.alifbee.kidsdemo.data.remote.mapper

import com.alifbee.kidsdemo.data.local.entity.ReviewEntity
import com.alifbee.kidsdemo.data.model.ReviewItem
import com.alifbee.kidsdemo.data.remote.dto.ReviewDto

fun ReviewDto.toEntity(): ReviewEntity = ReviewEntity(
    id = reviewId,
    text = text.orEmpty(),
    translation = translation,
    sound = sound,
    score = score ?: 0,
    isFavorite = isFavorite ?: false,
    levelId = levelId ?: 0,
    lessonId = lessonId ?: 0,
    lessonSlug = lessonSlug.orEmpty(),
)

fun ReviewEntity.toDomain(): ReviewItem = ReviewItem(
    id = id,
    text = text,
    translation = translation,
    sound = sound,
    score = score,
    isFavorite = isFavorite,
    levelId = levelId,
    lessonId = lessonId,
    lessonSlug = lessonSlug,
)
