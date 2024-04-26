package ru.cityron.domain.repository

import ru.cityron.domain.model.Info

interface UdpRepository {
    suspend fun receive(size: Int = 1024): Info
    fun close()
}