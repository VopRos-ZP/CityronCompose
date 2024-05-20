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
import ru.cityron.domain.model.JsonSched
import ru.cityron.domain.model.JsonSettings
import ru.cityron.domain.model.JsonState
import ru.cityron.domain.model.JsonStatic
import ru.cityron.domain.model.m3.M3Sched
import ru.cityron.domain.model.m3.M3Settings
import ru.cityron.domain.model.m3.M3State
import ru.cityron.domain.model.m3.M3Static
import ru.cityron.domain.repository.M3Repository
import ru.cityron.domain.repository.NetworkRepository
import ru.cityron.domain.utils.Path.JSON_STATE
import ru.cityron.domain.utils.Path.JSON_STATIC
import javax.inject.Inject

class M3RepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
) : M3Repository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override val state: Flow<M3State> = flow {
        sendRequests<JsonState<M3State>, M3State>(JSON_STATE) { state }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = M3State()
    )

    override val static: Flow<M3Static> = flow {
        sendRequests<JsonStatic<M3Static>, M3Static>(JSON_STATIC) { static }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = M3Static()
    )

    override val settings: Flow<M3Settings> = flow {
        sendRequests<JsonSettings<M3Settings>, M3Settings>("settings") { settings }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = M3Settings()
    )

    override val sched: Flow<M3Sched> = flow {
        sendRequests<JsonSched<M3Sched>, M3Sched>("sched") { sched }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = M3Sched()
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