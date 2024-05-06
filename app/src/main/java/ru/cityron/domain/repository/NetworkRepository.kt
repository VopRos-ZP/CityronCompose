package ru.cityron.domain.repository

interface NetworkRepository {
    suspend fun get(path: String): String
    suspend fun post(path: String, body: String)
}