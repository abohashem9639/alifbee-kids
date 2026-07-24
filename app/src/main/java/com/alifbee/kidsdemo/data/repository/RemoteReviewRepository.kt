package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.local.dao.ReviewDao
import com.alifbee.kidsdemo.data.model.ReviewItem
import com.alifbee.kidsdemo.data.remote.ApiService
import com.alifbee.kidsdemo.data.remote.mapper.toDomain
import com.alifbee.kidsdemo.data.remote.mapper.toEntity
import com.alifbee.kidsdemo.data.remote.safeApiCall
import com.alifbee.kidsdemo.data.result.BaseRes
import com.alifbee.kidsdemo.data.result.map
import com.alifbee.kidsdemo.data.result.onSuccess
import javax.inject.Inject

class RemoteReviewRepository @Inject constructor(
    private val api: ApiService,
    private val reviewDao: ReviewDao,
) : ReviewRepository {

    override suspend fun refreshReviews(): BaseRes<Unit> =
        safeApiCall { api.getReviews() }
            .onSuccess { body ->
                val entities = body.reviews.orEmpty().map { it.toEntity() }
                reviewDao.replaceAll(entities)
            }
            .map { }

    override suspend fun getAllReviews(): List<ReviewItem> =
        reviewDao.getAll().map { it.toDomain() }

    override suspend fun hasCachedReviews(): Boolean = reviewDao.count() > 0

    override suspend fun setFavorite(id: Int, favorite: Boolean) {
        reviewDao.setFavorite(id, favorite)
    }

    override suspend fun clearCache() {
        reviewDao.clear()
    }
}
