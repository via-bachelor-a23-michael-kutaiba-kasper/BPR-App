package io.github.viabachelora23michaelkutaibakasper.bprapp.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

fun parseUtcStringToLocalDateTime(utcString: String): LocalDateTime {
    val formatter: DateTimeFormatter = when {
        utcString.contains('.') && utcString.split('.').last().length == 9 -> {
            // If the string contains a dot and the last section has three digits, use the pattern for three digits
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        }
        utcString.contains('.') && utcString.split('.').last().length == 11 -> {
            // If the string contains a dot and the last section has six digits, use the pattern for six digits
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSXXX")
        }
        utcString.contains('.') && utcString.split('.').last().length == 12 -> {
            // If the string contains a dot and the last section has six digits, use the pattern for six digits
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
        }

        else -> {
            // If no dot or an unexpected format, use a fallback pattern
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
        }
    }
    return LocalDateTime.parse(utcString, formatter)
}

fun DisplayFormattedTime(
    dateTime: LocalDateTime
): String {
    val formatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.SHORT)
        .withLocale(Locale.UK)
    return dateTime.format(formatter)
}

fun convertUtcMillisecondsToFormattedDate(
    utcMilliseconds: Long?
): LocalDate {

    return Instant
        .ofEpochMilli(utcMilliseconds!!)
        .atOffset(
            ZoneOffset.UTC
        )
        .toLocalDate()
}