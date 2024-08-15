package ru.cityron.domain.repository

import ru.cityron.data.model.BindsAddResponse

interface BindRepository {
    suspend fun auth(num: String, appUuid: String): Boolean
    suspend fun auth(pass: String, level: Char): Boolean
    suspend fun add(accessLevel: String, appUuid: String, phoneInfo: String): String
    suspend fun confirm(code: String): BindsAddResponse
    suspend fun delete(num: String, appUuid: String): Boolean
    suspend fun cancel()
}