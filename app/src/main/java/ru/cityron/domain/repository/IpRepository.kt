package ru.cityron.domain.repository

import ru.cityron.domain.model.Ip

interface IpRepository {
    suspend fun fetchAll(): List<Ip>
    suspend fun upsert(ip: Ip)
    suspend fun delete(ip: Ip)
}