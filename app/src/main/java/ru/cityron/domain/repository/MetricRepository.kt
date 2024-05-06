package ru.cityron.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.cityron.domain.model.Chart

interface MetricRepository {
    val chart: Flow<Chart>
}