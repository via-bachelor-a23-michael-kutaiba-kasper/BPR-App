package io.github.viabachelora23michaelkutaibakasper.bprapp.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.math.roundToInt

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

fun Float.roundToNearestHalf(): Float {
    return (this * 2).roundToInt() / 2.0f
}

fun localDateTimeToUTCLocalDateTime(localDateTime: LocalDateTime): LocalDateTime? {
    return localDateTime.atOffset(ZoneOffset.UTC).toLocalDateTime()
}