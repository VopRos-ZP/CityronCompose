package ru.cityron.domain.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import ru.cityron.domain.model.Controller
import ru.cityron.domain.repository.ControllerRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetControllersUseCase @Inject constructor(
    private val controllerRepository: ControllerRepository
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val controllers: Flow<List<Controller>> = flow {
        while (true) {
            emit(controllerRepository.fetchAll())
            delay(1000)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

}