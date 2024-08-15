package ru.cityron.domain.usecase.all.alarms

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.cityron.data.room.all.alarms.M3AlarmsDatabase
import ru.cityron.data.room.all.alarms.fromDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetM3AlarmsUseCase @Inject constructor(
    private val m3AlarmsDatabase: M3AlarmsDatabase
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke(id: Int) = m3AlarmsDatabase.dao.fetchM3Alarm(id).fromDto()

    val flow = m3AlarmsDatabase.dao.listenM3Alarms()
        .map { list -> list.map { it.fromDto() } }
        .distinctUntilChanged()
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

}