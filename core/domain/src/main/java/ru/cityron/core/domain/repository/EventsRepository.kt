package ru.cityron.core.domain.repository

import ru.cityron.core.domain.model.LogResult

interface EventsRepository {
    suspend fun fetchEvents(
        count: Int,
        types: Int,
        sources: Int,
        reasons: Int
    ) : LogResult
}