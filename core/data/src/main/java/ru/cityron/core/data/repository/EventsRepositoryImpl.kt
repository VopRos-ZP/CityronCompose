package ru.cityron.core.data.repository

import kotlinx.serialization.json.Json
import ru.cityron.core.domain.model.LogResult
import ru.cityron.core.domain.repository.EventsRepository
import ru.cityron.core.domain.repository.NetworkRepository
import ru.cityron.core.domain.utils.Path
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository
): EventsRepository {

    override suspend fun fetchEvents(
        count: Int,
        types: Int,
        sources: Int,
        reasons: Int
    ): LogResult {
        val parameters = mapOf(
            "count" to count,
            "types" to types,
            "sources" to sources,
            "reasons" to reasons
        ).map { (k, v) -> "$k=$v" }.joinToString(separator = "&")
        val request = networkRepository.get("${Path.JSON_EVENTS}&$parameters")
        return Json.decodeFromString(request)
    }

}