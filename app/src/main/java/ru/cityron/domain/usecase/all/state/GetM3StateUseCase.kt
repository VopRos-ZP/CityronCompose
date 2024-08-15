package ru.cityron.domain.usecase.all.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.cityron.data.room.all.state.M3StateDatabase
import ru.cityron.data.room.all.state.fromDto
import ru.cityron.domain.model.m3.M3State
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetM3StateUseCase @Inject constructor(
    private val m3StateDatabase: M3StateDatabase
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke() = m3StateDatabase.dao.fetchM3State().fromDto()

    val flow = m3StateDatabase.dao.listenM3State()
        .map { it.fromDto() }
        .distinctUntilChanged()
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = M3State()
        )

}