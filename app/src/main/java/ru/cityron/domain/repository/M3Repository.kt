package ru.cityron.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.cityron.domain.model.m3.M3All
import ru.cityron.domain.model.m3.M3Sched
import ru.cityron.domain.model.m3.M3Settings
import ru.cityron.domain.model.m3.M3State
import ru.cityron.domain.model.m3.M3Static

interface M3Repository {
    val state: Flow<M3State>

    suspend fun getAll(): M3All
//    suspend fun getStatic(): M3Static
//    suspend fun getSettings(): M3Settings
//    suspend fun getSched(): M3Sched
    suspend fun getState(): M3State
}