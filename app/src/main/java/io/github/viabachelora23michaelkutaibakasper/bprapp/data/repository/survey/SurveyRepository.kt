package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.survey

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.BuildConfig
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetInterestSurveyQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.StoreInterestSurveyMutation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Status

class SurveyRepository: ISurveyRepository {


    override suspend fun getInterestSurvey(userId: String): Status {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(GetInterestSurveyQuery(userId = userId)).execute()
        Log.d(
            "ApolloEventClient",
            "GetInterestSurvey: ${response.data?.interestSurvey}"
        )
        return Status(
            message = response.data?.interestSurvey?.status?.message!!,
            code = response.data?.interestSurvey?.status?.code!!
        )
    }

    override suspend fun storeInterestSurvey(
        userId: String,
        keywords: List<String>,
        categories: List<String>
    ): Status {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.mutation(
            StoreInterestSurveyMutation(
                userId = userId,
                keywords = keywords,
                categories = categories
            )
        ).execute()
        Log.d(
            "ApolloEventClient",
            "StoreInterestSurvey: ${response.data?.storeInterestSurvey}"
        )
        return Status(
            message = response.data?.storeInterestSurvey?.status?.message!!,
            code = response.data?.storeInterestSurvey?.status?.code!!
        )
    }

}