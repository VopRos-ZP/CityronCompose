package ru.cityron.domain.usecase.all.stat

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.cityron.data.room.all.stat.M3StaticDatabase
import ru.cityron.data.room.all.stat.fromDto
import ru.cityron.domain.model.m3.M3Static
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetM3StaticUseCase @Inject constructor(
    private val m3StaticDatabase: M3StaticDatabase
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke() = m3StaticDatabase.dao.fetchM3Static().fromDto()

    val flow = m3StaticDatabase.dao.listenM3Static()
        .map { it.fromDto() }
        .distinctUntilChanged()
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = M3Static()
        )

}