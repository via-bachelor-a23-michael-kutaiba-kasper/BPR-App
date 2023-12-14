package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.review

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.EventRating

interface IReviewRepository {

    suspend fun createReview(eventId: Int, userId: String, rating: Float, reviewDate: String): Int
    suspend fun getReviewIds(userId: String): List<EventRating>
}