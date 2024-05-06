package ru.cityron.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.cityron.domain.model.Chart

interface MetricRepository {

    suspend fun chart(
        types: Int,
        sources: Int,
        values: Int
    ): Chart

}