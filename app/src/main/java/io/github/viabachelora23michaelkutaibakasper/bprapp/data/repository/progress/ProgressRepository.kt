package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.progress

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.BuildConfig
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetAllAchievementsQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetExperienceHistoryQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetExperienceQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetUserAchievementsQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Achievement
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Experience
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.ExperienceHistory
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.parseUtcStringToLocalDateTime

class ProgressRepository: IProgressRepository {


    override suspend fun getUserAchievements(userId: String): List<Achievement> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(GetUserAchievementsQuery(userId = userId)).execute()
        Log.d(
            "ApolloEventClient",
            "getUserAchievements: ${response.data?.userAchievements?.result}"
        )
        return response.data?.userAchievements?.result?.map {
            Achievement(
                icon = it?.icon!!,
                name = it.name!!,
                description = it.description!!,
                expReward = it.expReward!!,
                requirement = it.requirement!!,
                progress = it.progress!!,
                unlockDate = it.unlockDate?.let { it1 -> parseUtcStringToLocalDateTime(it1) },
                isAchieved = null
            )
        } ?: emptyList()

    }

    override suspend fun getAllAchievements(): List<Achievement> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(GetAllAchievementsQuery()).execute()
        Log.d("ApolloEventClient", "getKeywords: ${response.data?.allAchievements?.result}")
        return response.data?.allAchievements?.result?.map {
            Achievement(
                icon = it?.icon!!,
                name = it.name!!,
                description = it.description!!,
                expReward = it.expReward!!,
                progress = null,
                requirement = it.requirement!!,
                unlockDate = null,
                isAchieved = null
            )
        } ?: emptyList()
    }

    override suspend fun getExperience(userId: String): Experience {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(GetExperienceQuery(userId = userId)).execute()
        Log.d(
            "ApolloEventClient",
            "getExperience: ${response.data?.expProgress?.result}"
        )
        return Experience(
            totalExp = response.data?.expProgress?.result?.totalExp!!,
            level = response.data?.expProgress?.result?.level?.value!!,
            minExp = response.data?.expProgress?.result?.level!!.minExp!!,
            maxExp = response.data?.expProgress?.result?.level!!.maxExp!!,
            stage = response.data?.expProgress?.result?.stage!!,
            name = response.data?.expProgress?.result?.level!!.name!!
        )
    }

    override suspend fun getUserExperienceHistory(userId: String): List<ExperienceHistory> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(GetExperienceHistoryQuery(userId = userId)).execute()
        Log.d(
            "ApolloEventClient",
            "getUserExperienceHistory: ${response.data?.expProgress?.result}"
        )
        return response.data?.expProgress?.result?.expProgressHistory?.map {
            ExperienceHistory(
                exp = it?.expGained!!,
                date = parseUtcStringToLocalDateTime(it.timestamp!!)
            )
        } ?: emptyList()
    }

}