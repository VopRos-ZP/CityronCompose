package ru.cityron.core.domain.repository

interface IControllerRepository<A> : ConfRepository {
    suspend fun fetchAll(): Result<A>
}