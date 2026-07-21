package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.ReviewData
import com.alifbee.kidsdemo.data.remote.dto.ReviewDto
import com.alifbee.kidsdemo.data.remote.dto.ReviewGroupDto
import com.alifbee.kidsdemo.data.remote.dto.ReviewItemDto
import com.alifbee.kidsdemo.data.remote.mapper.toDomain
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeReviewRepository @Inject constructor() : ReviewRepository {

    override suspend fun getReview(): ReviewData {
        delay(SIMULATED_NETWORK_DELAY_MS)
        return fakeApiResponse().toDomain()
    }

    private fun fakeApiResponse(): ReviewDto = ReviewDto(
        itemsReadyCount = 38,
        groups = listOf(
            ReviewGroupDto(
                id = "words",
                title = "Items (24)",
                items = listOf(
                    ReviewItemDto("w1", "يتحرك", "Move", "cloud", false),
                    ReviewItemDto("w2", "انا سعيد جدا بلقائك.", "I am very glad to meet you.", "cloud", true),
                    ReviewItemDto("w3", "سحابة السماء", "Cloud sky", "cloud", false),
                    ReviewItemDto("w4", "ل", null, null, false),
                ),
            ),
            ReviewGroupDto(
                id = "letters",
                title = "Items (14)",
                items = listOf(
                    ReviewItemDto("l1", "يتحرك", "Move", "cloud", true),
                    ReviewItemDto("l2", "زهرة", "Flower", "hexagon", false),
                    ReviewItemDto("l3", "ث, ذ, ذ", null, "hexagon", false),
                    ReviewItemDto("l4", "قمر", "Moon", "cloud", false),
                    ReviewItemDto("l5", "شمس", "Sun", "cloud", true),
                ),
            ),
            ReviewGroupDto(
                id = "phrases",
                title = "Items (7)",
                items = listOf(
                    ReviewItemDto("p1", "صباح الخير", "Good morning", "cloud", false),
                    ReviewItemDto("p2", "مساء الخير", "Good evening", "cloud", true),
                    ReviewItemDto("p3", "كيف حالك؟", "How are you?", "cloud", false),
                ),
            ),
            ReviewGroupDto(
                id = "phrases",
                title = "Items (7)",
                items = listOf(
                    ReviewItemDto("p1", "صباح الخير", "Good morning", "cloud", false),
                    ReviewItemDto("p2", "مساء الخير", "Good evening", "cloud", true),
                    ReviewItemDto("p3", "كيف حالك؟", "How are you?", "cloud", false),
                ),
            ),
            ReviewGroupDto(
                id = "phrases",
                title = "Items (7)",
                items = listOf(
                    ReviewItemDto("p1", "صباح الخير", "Good morning", "cloud", false),
                    ReviewItemDto("p2", "مساء الخير", "Good evening", "cloud", true),
                    ReviewItemDto("p3", "كيف حالك؟", "How are you?", "cloud", false),
                ),
            ),
            ReviewGroupDto(
                id = "phrases",
                title = "Items (7)",
                items = listOf(
                    ReviewItemDto("p1", "صباح الخير", "Good morning", "cloud", false),
                    ReviewItemDto("p2", "مساء الخير", "Good evening", "cloud", true),
                    ReviewItemDto("p3", "كيف حالك؟", "How are you?", "cloud", false),
                ),
            ),
            ReviewGroupDto(
                id = "phrases",
                title = "Items (7)",
                items = listOf(
                    ReviewItemDto("p1", "صباح الخير", "Good morning", "cloud", false),
                    ReviewItemDto("p2", "مساء الخير", "Good evening", "cloud", true),
                    ReviewItemDto("p3", "كيف حالك؟", "How are you?", "cloud", false),
                ),
            ),
        ),
    )

    companion object {
        private const val SIMULATED_NETWORK_DELAY_MS = 400L
    }
}
