package ru.cityron.domain.usecase.events

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.cityron.data.room.events.EventDatabase
import ru.cityron.data.room.events.fromDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetEventsUseCase @Inject constructor(eventDatabase: EventDatabase) {

    private val scope = CoroutineScope(Dispatchers.IO)

    val flow = eventDatabase.eventDao.listen()
        .map { list -> list.map { it.fromDto() } }
        .distinctUntilChanged()
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

}