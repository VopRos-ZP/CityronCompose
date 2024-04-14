package ru.cityron.core.domain.repository

interface ConnectivityRepository {
    suspend fun isConnected(): Boolean
}