package ru.cityron.domain.usecase.events

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.cityron.data.room.events.EventDatabase
import ru.cityron.data.room.events.filter.fromDto
import ru.cityron.domain.model.Filter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFiltersUseCase @Inject constructor(
    private val eventDatabase: EventDatabase
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke() = eventDatabase.filterDao.fetch().fromDto()

    val flow = eventDatabase.filterDao.listen()
        .map { it.fromDto() }
        .distinctUntilChanged()
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = Filter()
        )

}