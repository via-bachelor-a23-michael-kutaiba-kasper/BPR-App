package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.recommendations

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent

interface IRecommendationsRepository {
    suspend fun getReccommendations(userId: String, numberOfEvents: Int): List<MinimalEvent>

}