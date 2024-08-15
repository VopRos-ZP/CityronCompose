package ru.cityron.data.repository

import kotlinx.serialization.json.Json
import ru.cityron.R
import ru.cityron.data.model.EventList
import ru.cityron.domain.model.Event
import ru.cityron.domain.repository.EventsRepository
import ru.cityron.domain.repository.NetworkRepository
import ru.cityron.domain.utils.Path.JSON_EVENTS
import ru.cityron.domain.utils.Path.toParameters
import ru.cityron.domain.utils.Temp
import ru.cityron.domain.utils.Time
import ru.cityron.domain.utils.Time.secondsToString
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository
): EventsRepository {

    override suspend fun fetchEvents(
        count: Int,
        types: Int,
        sources: Int,
        reasons: Int
    ): List<Event> {
        val params = mapOf(
            "count" to count,
            "types" to types,
            "sources" to sources,
            "reasons" to reasons,
        ).toParameters()

        val events = Json.decodeFromString<EventList>(networkRepository.get("$JSON_EVENTS&$params")).events

        return events.data.map {
            val datetime = secondsToString(it[0].toLong(), events.zone, Time.formatDatetime).split(" ")
            Event(
                date = datetime.first(),
                time = datetime.last(),
                type = when (it[1]) {
                    0 -> R.drawable.danger//"Авария"
                    1 -> R.drawable.config//"Конфигуарция"
                    2 -> R.drawable.setting_2//"Сервис"
                    3 -> R.drawable.tick_circle//"Работа"
                    else -> throw RuntimeException("")
                },
                text = when (Pair(it[1], it[3])) {
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
                    Pair(1, 0) -> "Изменена конфигурация"
                    Pair(1, 2) -> "Изменены настройки планировщика"
                    // Тип Сервис
                    Pair(2, 0) -> "Веб-интерфейс > Питание ${if (it[6] == 0) "отключено" else "восстановлено спустя ${it[5]}"}"
                    Pair(2, 1) -> "Алгоритм > Рестарт контроллера"
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
        }
    }

}