package ru.cityron.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.cityron.domain.model.m3.M3All
import ru.cityron.domain.model.m3.M3Sched
import ru.cityron.domain.model.m3.M3Settings
import ru.cityron.domain.model.m3.M3State
import ru.cityron.domain.model.m3.M3Static

interface M3Repository {
    val state: Flow<M3State>
    val static: Flow<M3Static>
    val settings: Flow<M3Settings>
    val sched: Flow<M3Sched>
}