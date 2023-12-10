package io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain

import android.net.Uri
import java.time.LocalDateTime

data class User(
    var displayName: String,
    var userId: String,
    var photoUrl: Uri?,
    var creationDate: LocalDateTime?,
    var lastSeenOnline: LocalDateTime?
)