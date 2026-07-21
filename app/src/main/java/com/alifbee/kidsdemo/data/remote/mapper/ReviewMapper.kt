package com.alifbee.kidsdemo.data.remote.mapper

import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.ReviewData
import com.alifbee.kidsdemo.data.model.ReviewGroup
import com.alifbee.kidsdemo.data.model.ReviewItem
import com.alifbee.kidsdemo.data.remote.dto.ReviewDto
import com.alifbee.kidsdemo.data.remote.dto.ReviewGroupDto
import com.alifbee.kidsdemo.data.remote.dto.ReviewItemDto

fun ReviewDto.toDomain(): ReviewData = ReviewData(
    itemsReadyCount = itemsReadyCount,
    groups = groups.map { it.toDomain() },
)

private fun ReviewGroupDto.toDomain() = ReviewGroup(
    id = id,
    title = title,
    items = items.map { it.toDomain() },
)

private fun ReviewItemDto.toDomain() = ReviewItem(
    id = id,
    arabic = arabic,
    english = english,
    iconRes = icon?.let { resolveReviewIcon(it) },
    isBookmarked = isBookmarked,
)

private fun resolveReviewIcon(name: String): Int? = when (name.lowercase()) {
    "cloud" -> R.drawable.ic_courses
    "hexagon", "letter" -> R.drawable.ic_medal
    "flower" -> R.drawable.ic_alifbee_academy
    else -> null
}
