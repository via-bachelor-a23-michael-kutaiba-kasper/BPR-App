package io.github.viabachelora23michaelkutaibakasper.bprapp.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.util.Locale

fun parseUtcStringToLocalDateTime(utcString: String): LocalDateTime {

    val formatter = DateTimeFormatter.ISO_DATE_TIME


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

)
        : LocalDate {

    return Instant
        .ofEpochMilli(utcMilliseconds!!)
        .atOffset(
            ZoneOffset.UTC
        )
        .toLocalDate()
}