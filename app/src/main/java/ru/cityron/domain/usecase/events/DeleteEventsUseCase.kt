package ru.cityron.domain.usecase.events

import ru.cityron.data.room.events.EventDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteEventsUseCase @Inject constructor(
    private val eventDatabase: EventDatabase
) {

    suspend operator fun invoke() {
        eventDatabase.eventDao.deleteAll()
    }

}