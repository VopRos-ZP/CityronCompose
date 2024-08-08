package ru.cityron.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json
import ru.cityron.domain.model.m3.M3All
import ru.cityron.domain.repository.M3Repository
import ru.cityron.domain.repository.NetworkRepository
import ru.cityron.domain.utils.Path.JSON_ALL
import javax.inject.Inject

class M3RepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
) : M3Repository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override val all: Flow<M3All> = flow {
        sendRequests<M3All, M3All>(JSON_ALL) { this }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = M3All()
    )

    private suspend inline fun <reified T, R> FlowCollector<R>.sendRequests(path: String, transform: T.() -> R) {
        while (true) {
            try {
                val result = fromJson<T>(networkRepository.get(path)).transform()
                emit(result)
            } catch (_: Exception) {}
            delay(1000)
        }
    }

    private inline fun <reified T> fromJson(string: String): T = Json.decodeFromString(string)

}