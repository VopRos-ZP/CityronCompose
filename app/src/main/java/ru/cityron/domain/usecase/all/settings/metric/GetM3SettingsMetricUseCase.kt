package ru.cityron.domain.usecase.all.settings.metric

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.cityron.data.room.all.settings.M3SettingsDatabase
import ru.cityron.data.room.all.settings.metric.fromDto
import ru.cityron.domain.model.Metric
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetM3SettingsMetricUseCase @Inject constructor(
    private val m3SettingsDatabase: M3SettingsDatabase
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke() = m3SettingsDatabase.metricDao.fetchSettingsMetric().fromDto()

    val flow = m3SettingsDatabase.metricDao.listenSettingsMetric()
        .map { it.fromDto() }
        .distinctUntilChanged()
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = Metric()
        )

}