package ru.cityron.domain.repository

interface CheckIpRepository {
    suspend fun checkIpAddress(ip: String): Boolean
}