package ru.cityron.domain.usecase.events

import ru.cityron.data.room.events.EventDatabase
import ru.cityron.data.room.events.toDto
import ru.cityron.domain.model.Filter
import ru.cityron.domain.repository.EventsRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.pow

@Singleton
class UpsertEventsUseCase @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val eventDatabase: EventDatabase
) {

    suspend operator fun invoke(filter: Filter) {
        val events = eventsRepository.fetchEvents(
            count = countIndexToValue(filter.count),
            types = indexToValue(filter.types),
            sources = indexToValue(filter.sources),
            reasons = indexToValue(filter.reasons)
        )
        events.forEachIndexed { i, event ->
            eventDatabase.eventDao.insert(event.copy(id = i + 1).toDto())
        }
    }

    private fun countIndexToValue(count: Int) = when (count) {
        1 -> 50
        2 -> 100
        3 -> 500
        4 -> 1000
        5 -> 1500
        else -> -1
    }

    private fun indexToValue(i: Int): Int = when (i) {
        0 -> -1
        else -> 2f.pow(i - 1).toInt()
    }

}