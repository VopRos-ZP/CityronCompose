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
import ru.cityron.domain.model.Status
import ru.cityron.domain.model.m3.M3State
import ru.cityron.domain.repository.ConnectivityRepository
import ru.cityron.domain.repository.ControllerRepository
import ru.cityron.domain.repository.HttpRepository
import javax.inject.Inject

class ConnectivityRepositoryImpl @Inject constructor(
    private val httpRepository: HttpRepository,
    private val controllerRepository: ControllerRepository
) :  ConnectivityRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override val controllersDataSources: Flow<Map<Controller, DataSource>> = flow {
        while (true) {
            val controllers = controllerRepository.fetchAll()
            emit(getControllersDataSources(controllers))
            delay(1000)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = emptyMap()
    )

    private suspend fun getControllersDataSources(controllers: List<Controller>): Map<Controller, DataSource> {
        return controllers.associateWith { getDataSourceByControllerType(it) }
    }

    private suspend fun getDataSourceByControllerType(controller: Controller): DataSource {
        return when (val name = controller.name.split(" ")[0]) {
            "M3" -> sendRequestAndGetSource<M3State>(controller) { alarms }
            //"atlas" -> sendRequestAndGetSource<AtlasState>(controller) { alarms }
            else -> throw RuntimeException("Doesn't have implementation for check connection for controller type $name")
        }
    }

    private suspend inline fun <reified T> sendRequestAndGetSource(controller: Controller, alarms: T.() -> Int): DataSource {
        val (result, func) = sendControllerRequest<JsonState<T>>(controller)
        return func(when {
            result == null -> Status.OFFLINE
            result.state.alarms() != 0 -> Status.ALERT
            else -> Status.ONLINE
        })
    }

    private suspend inline fun <reified T> sendControllerRequest(it: Controller): Pair<T?, (Status) -> DataSource> {
        return try {
            fromJson<T>(httpRepository.get("http://${it.ipAddress}/json?state")) to DataSource::Local
        } catch (_: Exception) {
            try {
                fromJson<T>(httpRepository.get("https://rcserver.ru/rc/${it.idCpu}/json?state")) to DataSource::Remote
            } catch (_: Exception) {
                null to DataSource::Remote
            }
        }
    }

    private inline fun <reified T> fromJson(string: String): T = Json.decodeFromString(string)

}