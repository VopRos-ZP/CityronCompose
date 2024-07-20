package ru.cityron.domain.repository

import ru.cityron.domain.model.EventWithDate

interface EventsRepository {
    suspend fun fetchEvents(
        count: Int,
        types: Int,
        sources: Int,
        reasons: Int
    ): List<EventWithDate>
}