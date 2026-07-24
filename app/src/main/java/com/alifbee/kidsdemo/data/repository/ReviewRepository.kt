package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.ReviewItem
import com.alifbee.kidsdemo.data.result.BaseRes

interface ReviewRepository {
    suspend fun refreshReviews(): BaseRes<Unit>
    suspend fun getAllReviews(): List<ReviewItem>
    suspend fun hasCachedReviews(): Boolean
    suspend fun setFavorite(id: Int, favorite: Boolean)
    suspend fun clearCache()
}
