package ru.cityron.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource

interface ConnectivityRepository {
    val controllersDataSources: Flow<Map<Controller, DataSource>>
}