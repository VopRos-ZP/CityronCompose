package com.vopros.cityron.ui.screens.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vopros.cityron.controller.ControllerItem
import com.vopros.cityron.repository.wifi.WifiRepository
import com.vopros.cityron.utils.Temp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.network.ControllerStateRepository
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
                                result = when (Pair(it[1], it[3])) {
                                    // Тип авария
                                    Pair(0, 0) -> "Питание отключено"
                                    Pair(0, 1) -> "Авария приточного вентилятора (DI1)"
                                    Pair(0, 2) -> "Авария вытяжного вентилятора (DI2)"
                                    Pair(0, 3) -> "Угроза замерзания по термостату/перегрев электронагревателя (DI3)"
                                    Pair(0, 4) -> "Засорение входного фильтра (DI4)"
                                    Pair(0, 5) -> "Пожарная тревога (DI5)"
                                    Pair(0, 6) -> "Ошибка датчика температуры в канале (NTC1)"
                                    Pair(0, 7) -> "Ошибка датчика температуры обратной воды (NTC3)"
                                    Pair(0, 8) -> "Температура меньше допустимой"
                                    Pair(0, 9) -> "Температура больше допустимой"
                                    // Тип Конфигурация
                                    Pair(1, 0) -> "Веб-интерфейс > Изменена конфигурация"
                                    Pair(1, 2) -> "Веб-интерфейс > Изменены настройки планировщика"
                                    // Тип Сервис
                                    Pair(2, 0) -> "Веб-интерфейс > Питание ${if (it[6] == 0) "отключено" else "восстановлено спустя ${it[5]}"}"
                                    Pair(2, 1) -> "Веб-интерфейс > Рестарт контроллера"
                                    Pair(2, 2) -> "Веб-интерфейс > Изменен режим доступа ${if (it[6] == 0) "Пользователь" else "Сервис"}"
                                    Pair(2, 3) -> "Веб-интерфейс > Обновление структуры конфигурации ${it[5]} -> ${it[6]}"
                                    Pair(2, 4) -> "Веб-интерфейс > Копирование конфигурации: "
                                    Pair(2, 5) -> "Веб-интерфейс > Восстановление конфигурации: "
                                    Pair(2, 6) -> "Веб-интерфейс > Сброс конфигурации"
                                    Pair(2, 7) -> "Веб-интерфейс > Прошивка загрузчика обновлена ${it[5]} -> ${it[6]}"
                                    Pair(2, 8) -> "Веб-интерфейс > Прошивка контроллера обновлена ${it[5]} -> ${it[6]}"
                                    Pair(2, 9) -> "Веб-интерфейс > Прошивка панели управления обновлена"
                                    Pair(2, 10) -> "Веб-интерфейс > Очистка лога событий"
                                    Pair(2, 11) -> "Веб-интерфейс > Очистка лога температуры"
                                    Pair(2, 12) -> "Веб-интерфейс > Сброс планировщика"
                                    // Тип Управление
                                    Pair(3, 0) -> "Веб-интерфейс > ${if (it[6] == 0) "СТОП" else "СТАРТ"} установки"
                                    Pair(3, 1) -> "Веб-интерфейс > Режим работы: ${if (it[6] == 0) "Обогрев" else "Вентиляция"}"
                                    Pair(3, 2) -> "Веб-интерфейс > Скорость вентилятора ${it[6]}"
                                    Pair(3, 3) -> "Веб-интерфейс > Уставка ${Temp.toGrade(it[6])}°C"
                                    Pair(3, 4) -> "Веб-интерфейс > Планировщик ${if (it[6] == 0) "ВЫКЛ" else "ВКЛ"}"
                                    // Other
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