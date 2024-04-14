package ru.cityron.core.domain.repository

interface BindRepository {
    suspend fun bindApps(ip: String): String
    suspend fun bindController(ip: String, uuid: String, deviceInfo: String): String
    suspend fun unbind(num: String, uuid: String): String
}