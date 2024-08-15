package ru.cityron.domain.usecase.all.sched

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.cityron.data.room.all.sched.M3SchedDatabase
import ru.cityron.data.room.all.sched.fromDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetM3TasksUseCase @Inject constructor(
    private val m3SchedDatabase: M3SchedDatabase
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke(id: Int) = m3SchedDatabase.dao.fetchM3Sched(id + 1).fromDto()

    val flow = m3SchedDatabase.dao.listenM3SchedList()
        .map { list -> list.map { it.fromDto() } }
        .distinctUntilChanged()
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

}