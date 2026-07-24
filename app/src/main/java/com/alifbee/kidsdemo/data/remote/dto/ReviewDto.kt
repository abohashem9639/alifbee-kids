package com.alifbee.kidsdemo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReviewsBody(
    @SerializedName("reviews") val reviews: List<ReviewDto>?,
)

data class ReviewDto(
    @SerializedName("reviewId") val reviewId: Int,
    @SerializedName("text") val text: String?,
    @SerializedName("translation") val translation: String?,
    @SerializedName("sound") val sound: String?,
    @SerializedName("score") val score: Int?,
    @SerializedName("levelId") val levelId: Int?,
    @SerializedName("isFavorite") val isFavorite: Boolean?,
    @SerializedName("lessonId") val lessonId: Int?,
    @SerializedName("lessonSlug") val lessonSlug: String?,
)
