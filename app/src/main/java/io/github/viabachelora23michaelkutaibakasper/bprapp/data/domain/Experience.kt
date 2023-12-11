package io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain

import java.time.LocalDateTime


data class Experience(
    val totalExp: Int,
    val level: Int,
    val minExp: Int,
    val maxExp: Int,
)

data class ExperienceHistory(
    val exp: Int,
    val date: LocalDateTime
)