package ru.cityron.domain.repository

interface HttpRepository {
    suspend fun get(url: String): String
    suspend fun post(url: String, body: String): String
}