package ru.cityron.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.cityron.domain.model.Chart
import ru.cityron.domain.repository.HttpRepository
import ru.cityron.domain.repository.MetricRepository
import javax.inject.Inject

class MetricRepositoryImpl @Inject constructor(
    private val httpRepository: HttpRepository
): MetricRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    override val chart: Flow<Chart> = flow {
        httpRepository.get("events")
    }

}