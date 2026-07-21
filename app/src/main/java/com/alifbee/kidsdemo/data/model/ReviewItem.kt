package com.alifbee.kidsdemo.data.model

import androidx.annotation.DrawableRes

data class ReviewItem(
    val id: String,
    val arabic: String,
    val english: String?,
    @DrawableRes val iconRes: Int?,
    val isBookmarked: Boolean,
)
