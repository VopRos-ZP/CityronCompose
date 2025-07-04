package ru.cityron.domain.usecase.controller

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.cityron.data.room.all.Table
import ru.cityron.data.room.controller.ControllerDatabase
import ru.cityron.data.room.controller.toController
import ru.cityron.domain.model.Controller
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetControllersUseCase @Inject constructor(
    private val controllerDatabase: ControllerDatabase
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke() = controllerDatabase.dao.fetchAll().map { c -> c.toController() }

    fun listenOne(id: Int = Table.ID) = controllerDatabase.dao.listenOne(id)
        .map { it.toController() }
        .distinctUntilChanged()
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = Controller()
        )

    fun controllers(): Flow<List<Controller>> = controllerDatabase.dao
        .listenAll()
        .map { list -> list.map { it.toController() } }
        .distinctUntilChanged()
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

}