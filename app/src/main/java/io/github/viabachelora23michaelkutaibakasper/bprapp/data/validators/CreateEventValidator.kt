package io.github.viabachelora23michaelkutaibakasper.bprapp.data.validators

import java.time.LocalDateTime

fun isValidTitle(text: String): Boolean {
    return text.isEmpty()
}

fun isValidDescription(text: String): Boolean {
    return text.isEmpty()
}
fun isValidAddress(address: String): Boolean {
    return address.isEmpty()
}
fun isValidCategory(category: String): Boolean {
    return category == "Choose Category"
}

fun isValidKeywords(keywords: List<String>): Boolean {
    return keywords.size < 3 || keywords.size > 5
}
fun isEndDateBeforeStartDate(
    startDate: LocalDateTime,
    endDate: LocalDateTime
): Boolean {
    return endDate.isBefore(startDate)
}