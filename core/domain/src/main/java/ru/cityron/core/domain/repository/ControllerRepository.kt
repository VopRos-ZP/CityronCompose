package ru.cityron.core.domain.repository

import ru.cityron.core.domain.model.Controller

interface ControllerRepository {
    suspend fun fetchAllController(): List<Controller>
    suspend fun fetchController(id: Int): Controller
    suspend fun upsertController(controller: Controller)
    suspend fun deleteController(controller: Controller)
}