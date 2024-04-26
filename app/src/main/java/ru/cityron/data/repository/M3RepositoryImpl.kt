package ru.cityron.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json
import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource
import ru.cityron.domain.model.JsonState
import ru.cityron.domain.model.m3.M3All
import ru.cityron.domain.model.m3.M3State
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.repository.HttpRepository
import ru.cityron.domain.repository.M3Repository
import javax.inject.Inject

class M3RepositoryImpl @Inject constructor(
    private val httpRepository: HttpRepository,
    private val currentRepository: CurrentRepository
) : M3Repository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override val state: Flow<M3State> = flow {
        while (true) {
            val (controller, source) = currentRepository.current!!
            val result = sendControllerRequest<JsonState<M3State>>(
                controller, source, "json?state"
            ).state
            emit(result)
            delay(1000)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = M3State()
    )

    override suspend fun getAll(): M3All {
        val (controller, source) = currentRepository.current!!
        return sendControllerRequest(controller, source, "json?all")
    }

    override suspend fun getState(): M3State {
        val (controller, source) = currentRepository.current!!
        return sendControllerRequest<JsonState<M3State>>(
            controller, source, "json?state"
        ).state
    }

    private suspend inline fun <reified T> sendControllerRequest(
        it: Controller, source: DataSource, suffix: String
    ): T {
        return when (source) {
            is DataSource.Local -> fromJson<T>(httpRepository.get("http://${it.ipAddress}/$suffix"))
            is DataSource.Remote -> fromJson<T>(httpRepository.get("https://rcserver.ru/rc/${it.idCpu}/json?state"))
        }
    }

    private inline fun <reified T> fromJson(string: String): T = Json.decodeFromString(string)

}