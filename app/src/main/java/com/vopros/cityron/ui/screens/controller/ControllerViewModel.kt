package com.vopros.cityron.ui.screens.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vopros.cityron.controller.ControllerItem
import com.vopros.cityron.repository.controllerState.ControllerStateRepository
import com.vopros.cityron.repository.wifi.WifiRepository
import com.vopros.cityron.utils.Temp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

abstract class ControllerViewModel<T> (
    private val wifiRepository: WifiRepository,
    private val localStateRepository: ControllerStateRepository,
    private val serverStateRepository: ControllerStateRepository,
    private val convert: (String) -> T,
    default: T? = null
) : ViewModel() {

    private val _state = MutableStateFlow(default)
    val state = _state.asStateFlow()

    private val _events = MutableStateFlow<Map<String, List<EventUseCase>>?>(null)
    val events = _events.asStateFlow()

    private var isRunState = true
    private var isRunEvent = true

    fun fetchState(controller: ControllerItem) {
        isRunState = true
        viewModelScope.launch {
            useRepository(controller) { repo, id ->
                while (isRunState) {
                    try {
                        _state.emit(convert(repo.getState(id)))
                    } catch (e: Exception) {
                        _state.emit(null)
                    }
                    delay(1000)
                }
            }
        }
    }

    fun disposeState() {
        isRunState = false
    }

    fun updateState(controller: ControllerItem, key: String, value: Any) {
        viewModelScope.launch {
            useRepository(controller) { repo, id ->
                repo.updateState(id, key, value)
            }
        }
    }

    fun fetchLog(
        controller: ControllerItem,
        count: Int,
        types: Int,
        sources: Int,
        reasons: Int
    ) {
        isRunEvent = true
        viewModelScope.launch {
            useRepository(controller) { repo, id ->
                while (isRunEvent) {
                    try {
                        val res = repo.fetchLog(id, count, types, sources, reasons)
                        val map = mutableMapOf<String, List<EventUseCase>>()
                        res.events.data.map {
                            EventUseCase(
                                date = Instant.ofEpochSecond(it[0].toLong()).atZone(ZoneId.systemDefault()).toLocalDateTime(),
                                type = when (it[1]) {
                                    0 -> "Авария"
                                    1 -> "Конфигуарция"
                                    2 -> "Сервис"
                                    3 -> "Работа"
                                    else -> ""
                                },
                                result = when (Triple(it[3], it[4], it[5])) {
                                    Triple(3, 8, 0) -> "Веб-интерфейс > Уставка ${Temp.toGrade(it[6])}°C"

                                    else -> ""
                                }
                            )
                        }.forEach {
                            val dmy = "${it.date.dayOfMonth}/${it.date.monthValue}/${it.date.year}"
                            map[dmy] = (map[dmy]?.toMutableList() ?: mutableListOf()).apply { add(it) }
                        }
                        _events.emit(map)
                    } catch (e: Exception) {
                        _events.emit(null)
                    }
                    delay(1000)
                }
            }
        }
    }

    fun disposeEvent() {
        isRunEvent = false
    }

    private suspend fun useRepository(controller: ControllerItem, callback: suspend (ControllerStateRepository, String) -> Unit) {
        wifiRepository.isInLocalNetwork().collect {
            val (repo, id) = when (it) {
                true -> Pair(localStateRepository, controller.ipAddress)
                else -> Pair(serverStateRepository, controller.idCpu)
            }
            callback(repo, id)
        }
    }

}