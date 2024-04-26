package ru.cityron.domain.repository

import ru.cityron.domain.model.Controller

interface ControllerRepository {
    suspend fun fetchAll(): List<Controller>
    suspend fun upsert(controller: Controller)
    suspend fun delete(id: Int)
}