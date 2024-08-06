package ru.cityron.domain.repository

interface ConfRepository {
    suspend fun conf(key: String, value: Any)
}