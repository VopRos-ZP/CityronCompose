package ru.cityron.domain.usecase.all.settings.metric

import ru.cityron.data.room.all.settings.M3SettingsDatabase
import ru.cityron.data.room.all.settings.metric.toDto
import ru.cityron.domain.model.Metric
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertM3SettingsMetricUseCase @Inject constructor(
    private val m3SettingsDatabase: M3SettingsDatabase
) {

    suspend operator fun invoke(
        metric: Metric,
        metricMin: Metric,
        metricMax: Metric,
    ) {
        m3SettingsDatabase.metricDao.upsertSettingsMetric(metric.toDto(
            frequencyMin = metricMin.frequency,
            frequencyMax = metricMax.frequency,
            valuesBitsMin = metricMin.valuesBits,
            valuesBitsMax = metricMax.valuesBits
        ))
    }

}