package ru.cityron.core.data.repository

import ru.cityron.core.data.local.ControllerDatabase
import ru.cityron.core.data.local.ControllerEntity
import ru.cityron.core.data.mappers.toController
import ru.cityron.core.data.mappers.toEntity
import ru.cityron.core.domain.model.Controller
import ru.cityron.core.domain.repository.ControllerRepository
import javax.inject.Inject

class ControllerRepositoryImpl @Inject constructor(
    private val db: ControllerDatabase
) : ControllerRepository {

    override suspend fun fetchAllController(): List<Controller> {
        return db.dao.fetchAllController()
                .map(ControllerEntity::toController)
    }

    override suspend fun fetchController(id: Int): Controller {
        return db.dao.fetchControllerById(id).toController()
    }

    override suspend fun upsertController(controller: Controller) {
        db.dao.upsertController(controller.toEntity())
    }

    override suspend fun deleteController(controller: Controller) {
        db.dao.deleteController(controller.toEntity())
    }

}