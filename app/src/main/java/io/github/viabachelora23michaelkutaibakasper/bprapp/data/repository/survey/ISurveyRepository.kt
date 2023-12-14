package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.survey

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Status

interface ISurveyRepository {
    suspend fun getInterestSurvey(userId: String): Status
    suspend fun storeInterestSurvey(
        userId: String,
        keywords: List<String>,
        categories: List<String>
    ): Status

}