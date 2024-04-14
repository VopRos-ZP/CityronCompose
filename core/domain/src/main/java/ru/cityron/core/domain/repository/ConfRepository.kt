package ru.cityron.core.domain.repository

interface ConfRepository {
    suspend fun conf(key: String, value: Any): Result<String>
}