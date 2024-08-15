package ru.cityron.domain.repository

import ru.cityron.domain.model.Event

interface EventsRepository {
    suspend fun fetchEvents(
        count: Int,
        types: Int,
        sources: Int,
        reasons: Int
    ): List<Event>
}