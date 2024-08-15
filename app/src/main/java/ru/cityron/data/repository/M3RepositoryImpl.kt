package ru.cityron.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json
import ru.cityron.domain.model.m3.M3All
import ru.cityron.domain.repository.M3Repository
import ru.cityron.domain.repository.NetworkRepository
import ru.cityron.domain.utils.Path.JSON_ALL
import ru.cityron.domain.utils.fromJson
import javax.inject.Inject

class M3RepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
) : M3Repository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override val all = flow<M3All> {
        while (true) {
            emit(fromJson(networkRepository.get(JSON_ALL)))
            delay(1000)
        }
    }.distinctUntilChanged().stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = M3All()
    )

}