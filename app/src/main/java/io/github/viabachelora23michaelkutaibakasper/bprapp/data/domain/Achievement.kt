package io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain

import java.time.LocalDateTime


data class Achievement(
    val icon: String,
    val name: String,
    val description: String,
    val expReward: Int,
    val requirement: Int,
    var progress: Int?,
    var unlockDate: LocalDateTime?,
    var isAchieved: Boolean?
)
