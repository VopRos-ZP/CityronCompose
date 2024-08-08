package ru.cityron.domain.repository

import ru.cityron.domain.model.Chart

interface MetricRepository {

    suspend fun chart(
        start: Long,
        end: Long,
        types: Int,
        sources: Int,
        values: Int
    ): Chart

}