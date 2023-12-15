package io.github.viabachelora23michaelkutaibakasper.bprapp.mocks

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Status
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.survey.ISurveyRepository

class FakeSurveyRepository: ISurveyRepository {

    override suspend fun getInterestSurvey(userId: String): Status {
        return Status("Success", 200)
    }

    override suspend fun storeInterestSurvey(
        userId: String,
        keywords: List<String>,
        categories: List<String>
    ): Status {
        return Status("Success", 200)
    }
}