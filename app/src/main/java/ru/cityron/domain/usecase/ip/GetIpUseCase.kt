package ru.cityron.domain.usecase.ip

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import ru.cityron.domain.model.Ip
import ru.cityron.domain.repository.IpRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetIpUseCase @Inject constructor(
    private val ipRepository: IpRepository
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val addresses: Flow<List<Ip>> = flow {
        while (true) {
            emit(ipRepository.fetchAll())
            delay(1000)
        }
    }.distinctUntilChanged().stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

}