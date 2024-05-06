package ru.cityron.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import ru.cityron.domain.model.Chart
import ru.cityron.domain.model.MetricChart
import ru.cityron.domain.repository.HttpRepository
import ru.cityron.domain.repository.MetricRepository
import ru.cityron.domain.repository.NetworkRepository
import ru.cityron.domain.utils.Path.JSON_CHART
import ru.cityron.domain.utils.Path.toParameters
import javax.inject.Inject

class MetricRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository
): MetricRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun chart(types: Int, sources: Int, values: Int): Chart {
        val params = mapOf(
            "types" to types,
            "sources" to sources,
            "values" to values
        ).toParameters()
        return networkRepository.get("$JSON_CHART&$params").fromJson<MetricChart>().chart
    }

    private inline fun <reified T> String.fromJson(): T = Json.decodeFromString(this)

}