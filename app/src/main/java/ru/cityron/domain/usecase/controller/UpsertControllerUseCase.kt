package ru.cityron.domain.usecase.controller

import ru.cityron.data.room.controller.ControllerDatabase
import ru.cityron.data.room.controller.toDto
import ru.cityron.domain.model.Controller
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertControllerUseCase @Inject constructor(
    private val controllerDatabase: ControllerDatabase
) {

    suspend operator fun invoke(controller: Controller) {
        controllerDatabase.dao.upsert(controller.toDto())
    }

}