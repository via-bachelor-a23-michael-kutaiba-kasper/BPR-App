package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.review

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import io.github.viabachelora23michaelkutaibakasper.bprapp.BuildConfig
import io.github.viabachelora23michaelkutaibakasper.bprapp.CreateReviewMutation
import io.github.viabachelora23michaelkutaibakasper.bprapp.ReviewsByUserQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.EventRating

class ReviewRepository: IReviewRepository {

    override suspend fun createReview(
        eventId: Int,
        userId: String,
        rating: Float,
        reviewDate: String
    ): Int {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response =
            apolloClient.mutation(
                CreateReviewMutation(
                    eventId = eventId,
                    rate = rating.toDouble(),
                    reviewerId = userId,
                    reviewDate = reviewDate
                )
            ).execute()

        return response.data?.createReview?.result?.id!!
    }

    override suspend fun getReviewIds(userId: String): List<EventRating> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response =
            apolloClient.query(ReviewsByUserQuery(userId = Optional.presentIfNotNull(userId)))
                .execute()
        Log.d(
            "ApolloEventClient",
            "getReviewIds: ${response.data?.reviewsByUser?.result}"
        )
        return response.data?.reviewsByUser?.result?.map {
            EventRating(
                eventId = it?.eventId!!,
                rating = it.rate!!.toFloat()
            )
        } ?: emptyList()
    }
}