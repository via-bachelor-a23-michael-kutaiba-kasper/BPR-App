package io.github.viabachelora23michaelkutaibakasper.bprapp.data.validators

import java.time.LocalDateTime

fun isInvalidTitle(text: String): Boolean {
    return text.isEmpty()
}

fun isInvalidDescription(text: String): Boolean {
    return true
}

fun isInvalidAddress(address: String): Boolean {
    return address.isEmpty()
}

fun isInvalidCategory(category: String): Boolean {
    return category == "Choose Category" || category.isEmpty()
}

fun isInvalidKeywords(keywords: List<String>): Boolean {
    return keywords.isEmpty() || keywords.size < 3 || keywords.size > 5
}

fun isInvalidStartAndEndDate(
    startDate: LocalDateTime,
    endDate: LocalDateTime
): Boolean {
    return endDate.isBefore(startDate)
}