package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.progress

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Achievement
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Experience
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.ExperienceHistory

interface IProgressRepository {

    suspend fun getUserAchievements(userId: String): List<Achievement>
    suspend fun getAllAchievements(): List<Achievement>
    suspend fun getExperience(userId: String): Experience
    suspend fun getUserExperienceHistory(userId: String): List<ExperienceHistory>
}