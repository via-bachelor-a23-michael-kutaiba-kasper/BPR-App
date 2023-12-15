package io.github.viabachelora23michaelkutaibakasper.bprapp.mocks

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Achievement
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Experience
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.ExperienceHistory
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.progress.IProgressRepository
import java.time.LocalDateTime

class FakeProgressRepository: IProgressRepository{


    override suspend fun getUserAchievements(userId: String): List<Achievement> {
        return listOf(
            Achievement(
                name = "Music Maestro",
                description = "Host 5 music events",
                expReward = 10,
                icon = "",
                requirement = 1, progress = 2,
                unlockDate = LocalDateTime.now(),
                isAchieved = false
            ),
            Achievement(
                name = "Music Maestro",
                description = "Host 5 music events",
                expReward = 10,
                icon = "",
                requirement = 1, progress = 2,
                unlockDate = LocalDateTime.now(),
                isAchieved = false
            ), Achievement(
                name = "Music Maestro",
                description = "Host 5 music events",
                expReward = 10,
                icon = "",
                requirement = 1, progress = 2,
                unlockDate = LocalDateTime.now(),
                isAchieved = false
            ),
            Achievement(
                name = "Music Maestro",
                description = "Host 5 music events",
                expReward = 10,
                icon = "",
                requirement = 1, progress = 2,
                unlockDate = LocalDateTime.now(),
                isAchieved = false
            )
        )
    }

    override suspend fun getAllAchievements(): List<Achievement> {
        return listOf(
            Achievement(
                name = "Music Maestro",
                description = "Host 5 music events",
                expReward = 10,
                icon = "",
                requirement = 1, progress = 2,
                unlockDate = LocalDateTime.now(),
                isAchieved = false
            ),
            Achievement(
                name = "Music Maestro",
                description = "Host 5 music events",
                expReward = 10,
                icon = "",
                requirement = 1, progress = 2,
                unlockDate = LocalDateTime.now(),
                isAchieved = false
            ), Achievement(
                name = "Music Maestro",
                description = "Host 5 music events",
                expReward = 10,
                icon = "",
                requirement = 1, progress = 2,
                unlockDate = LocalDateTime.now(),
                isAchieved = false
            ),
            Achievement(
                name = "Music Maestro",
                description = "Host 5 music events",
                expReward = 10,
                icon = "",
                requirement = 1, progress = 2,
                unlockDate = LocalDateTime.now(),
                isAchieved = false
            )
        )
    }

    override suspend fun getExperience(userId: String): Experience {
        return Experience(
            totalExp = 100,
            level = 1,
            maxExp = 200,
            minExp = 100,
            stage = 1,
            name = "Beginner"
        )
    }

    override suspend fun getUserExperienceHistory(userId: String): List<ExperienceHistory> {
        return listOf(
            ExperienceHistory(
                exp = 100,
                date = LocalDateTime.now()
            ),
            ExperienceHistory(
                exp = 100,
                date = LocalDateTime.now()
            ),
            ExperienceHistory(
                exp = 100,
                date = LocalDateTime.now()
            ),
        )
    }
}