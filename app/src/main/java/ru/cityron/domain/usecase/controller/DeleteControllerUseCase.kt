package ru.cityron.domain.usecase.controller

import ru.cityron.data.room.controller.ControllerDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteControllerUseCase @Inject constructor(
    private val controllerDatabase: ControllerDatabase
) {

    suspend operator fun invoke(id: Int) {
        controllerDatabase.dao.delete(id)
    }

}