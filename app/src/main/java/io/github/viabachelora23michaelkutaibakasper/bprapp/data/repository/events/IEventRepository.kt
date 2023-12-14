package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.events

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Achievement
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.EventRating
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Experience
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.ExperienceHistory
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Status

interface IEventRepository {
    suspend fun getEvents(
        hostId: String? = null,
        includePrivate: Boolean? = null,
        from: String? = null,
    ): List<MinimalEvent>
    suspend fun getEvent(eventId: Int): Event
    suspend fun createEvent(event: Event): Int
    suspend fun joinEvent(eventId: Int, userId: String)
    suspend fun getJoinedEvents(userId: String, eventState: String): List<MinimalEvent>


}