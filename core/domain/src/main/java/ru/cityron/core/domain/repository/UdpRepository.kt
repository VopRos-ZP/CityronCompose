package ru.cityron.core.domain.repository

import kotlinx.coroutines.Deferred
import ru.cityron.core.domain.model.Info

interface UdpRepository {
    suspend fun receive(size: Int = 1024): Deferred<Info>
    fun close()
}