package ru.cityron.data.repository

import ru.cityron.data.room.ControllerDatabase
import ru.cityron.data.room.ControllerDto
import ru.cityron.data.room.toController
import ru.cityron.data.room.toDto
import ru.cityron.domain.model.Controller
import ru.cityron.domain.repository.ControllerRepository
import javax.inject.Inject

class ControllerRepositoryImpl @Inject constructor(
    private val database: ControllerDatabase
) : ControllerRepository {

    override suspend fun fetchAll(): List<Controller> {
        return database.dao.fetchAll().map(ControllerDto::toController)
    }

    override suspend fun upsert(controller: Controller) {
        database.dao.upsert(controller.toDto())
    }

    override suspend fun delete(id: Int) {
        database.dao.delete(id)
    }

}