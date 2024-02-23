package com.vopros.cityron.repository.wifi

import kotlinx.coroutines.flow.Flow

interface WifiRepository {
    suspend fun isInLocalNetwork(): Flow<Boolean>
}