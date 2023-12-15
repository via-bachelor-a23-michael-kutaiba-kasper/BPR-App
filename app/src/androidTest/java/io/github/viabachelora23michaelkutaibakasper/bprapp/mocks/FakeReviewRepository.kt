package io.github.viabachelora23michaelkutaibakasper.bprapp.mocks

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.EventRating
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.review.IReviewRepository

class FakeReviewRepository: IReviewRepository {

    override suspend fun createReview(
        eventId: Int,
        userId: String,
        rating: Float,
        reviewDate: String
    ): Int {
        return 1
    }

    override suspend fun getReviewIds(userId: String): List<EventRating> {
        return listOf(
            EventRating(1, 4.5f),
            EventRating(2, 3.5f),
            EventRating(3, 2.5f),
            EventRating(4, 1.5f),
            EventRating(5, 0.5f)
        )
    }
}