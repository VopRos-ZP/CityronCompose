package ru.cityron.core.domain.repository

interface NetworkRepository : ConfRepository {
    suspend fun get(path: String): String
    suspend fun post(path: String, body: String? = null): String
}