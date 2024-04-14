package ru.cityron.core.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.cityron.core.domain.model.Setting

interface LocalStoreRepository {
    val setting: Flow<Setting>
    suspend fun emitSetting(setting: Setting)
}