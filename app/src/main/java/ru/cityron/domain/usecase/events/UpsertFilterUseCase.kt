package ru.cityron.domain.usecase.events

import ru.cityron.data.room.events.EventDatabase
import ru.cityron.data.room.events.filter.toDto
import ru.cityron.domain.model.Filter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertFilterUseCase @Inject constructor(
    private val eventDatabase: EventDatabase
) {

    suspend operator fun invoke(filter: Filter) {
        eventDatabase.filterDao.upsert(filter.toDto())
    }

}