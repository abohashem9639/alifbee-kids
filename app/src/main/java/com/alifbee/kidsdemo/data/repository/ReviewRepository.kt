package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.ReviewData

interface ReviewRepository {
    suspend fun getReview(): ReviewData
}
