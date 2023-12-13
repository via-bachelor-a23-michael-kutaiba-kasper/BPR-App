package io.github.viabachelora23michaelkutaibakasper.bprapp.util


import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.math.roundToInt
import kotlin.random.Random

fun parseUtcStringToLocalDateTime(utcString: String): LocalDateTime {

    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val somethinh =
        LocalDateTime.parse(utcString, formatter).atOffset(ZoneOffset.UTC).toLocalDateTime()
    return somethinh.atZone(ZoneId.of("Europe/Copenhagen")).toLocalDateTime()
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

    val zonedDateTimeLocal = ZonedDateTime.of(localDateTime, ZoneId.of("Europe/Copenhagen"))
    val zonedDateTimeUtc = zonedDateTimeLocal.withZoneSameInstant(ZoneId.of("UTC"))
    return zonedDateTimeUtc.toLocalDateTime()
}

class GreyScaleModifier : DrawModifier {
    override fun ContentDrawScope.draw() {

        val saturationMatrix =
            androidx.compose.ui.graphics.ColorMatrix().apply { setToSaturation(0f) }

        val saturationFilter = ColorFilter.colorMatrix(saturationMatrix)
        val paint = Paint().apply {
            colorFilter = saturationFilter
        }
        drawIntoCanvas {
            it.saveLayer(Rect(0f, 0f, size.width, size.height), paint)
            drawContent()
            it.restore()
        }
    }
}

fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun Modifier.greyScale() = this.then(GreyScaleModifier())
fun generateRandomColor(): Color {
    val random = Random.Default
    return Color(
        red = random.nextFloat(),
        green = random.nextFloat(),
        blue = random.nextFloat(),
    )

}

