package com.alifbee.kidsdemo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("items_ready_count") val itemsReadyCount: Int,
    @SerializedName("groups") val groups: List<ReviewGroupDto>,
)

data class ReviewGroupDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("items") val items: List<ReviewItemDto>,
)

data class ReviewItemDto(
    @SerializedName("id") val id: String,
    @SerializedName("arabic") val arabic: String,
    @SerializedName("english") val english: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("is_bookmarked") val isBookmarked: Boolean,
)
