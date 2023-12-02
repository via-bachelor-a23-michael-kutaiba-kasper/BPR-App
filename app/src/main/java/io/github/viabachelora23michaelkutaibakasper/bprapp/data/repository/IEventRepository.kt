package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent

interface IEventRepository {
    suspend fun getEvents(): List<MinimalEvent>
    suspend fun getEvent(eventId: Int): Event
    suspend fun createEvent(event: Event): Int
    suspend fun joinEvent(eventId: Int, userId: String)

    suspend fun getKeywords(): List<String>
    suspend fun getCategories(): List<String>
}