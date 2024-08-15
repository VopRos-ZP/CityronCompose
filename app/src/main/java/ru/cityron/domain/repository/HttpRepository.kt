package ru.cityron.domain.repository

interface HttpRepository {
    suspend fun get(
        url: String,
        token: String? = null
    ): String
    suspend fun post(
        url: String,
        token: String?  = null,
        body: String
    ): String
}