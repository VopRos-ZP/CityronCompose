package ru.cityron.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface WifiRepository {
    suspend fun isInLocalNetwork(): Flow<Boolean>
}