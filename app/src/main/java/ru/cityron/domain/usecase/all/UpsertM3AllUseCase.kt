package ru.cityron.domain.usecase.all

import ru.cityron.domain.model.m3.M3All
import ru.cityron.domain.usecase.algo.UpsertM3AlgoUseCase
import ru.cityron.domain.usecase.all.alarms.UpsertM3AlarmsUseCase
import ru.cityron.domain.usecase.all.sched.UpsertM3TaskUseCase
import ru.cityron.domain.usecase.all.settings.eth.UpsertM3SettingsEthUseCase
import ru.cityron.domain.usecase.all.settings.http.UpsertM3SettingsHttpUseCase
import ru.cityron.domain.usecase.all.settings.metric.UpsertM3SettingsMetricUseCase
import ru.cityron.domain.usecase.all.settings.time.UpsertM3SettingsTimeUseCase
import ru.cityron.domain.usecase.all.stat.UpsertM3StaticUseCase
import ru.cityron.domain.usecase.all.state.UpsertM3StateUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertM3AllUseCase @Inject constructor(
    private val upsertM3AlgoUseCase: UpsertM3AlgoUseCase,
    private val upsertM3TaskUseCase: UpsertM3TaskUseCase,
    private val upsertM3StateUseCase: UpsertM3StateUseCase,
    private val upsertM3AlarmsUseCase: UpsertM3AlarmsUseCase,
    private val upsertM3StaticUseCase: UpsertM3StaticUseCase,
    private val upsertM3SettingsEthUseCase: UpsertM3SettingsEthUseCase,
    private val upsertM3SettingsHttpUseCase: UpsertM3SettingsHttpUseCase,
    private val upsertM3SettingsTimeUseCase: UpsertM3SettingsTimeUseCase,
    private val upsertM3SettingsMetricUseCase: UpsertM3SettingsMetricUseCase,
) {

    suspend operator fun invoke(all: M3All) {
        upsertAlgo(all)
        upsertState(all)
        upsertTasks(all)
        upsertStatic(all)
        upsertAlarms(all)
        upsertSettings(all)
    }

    private suspend fun upsertAlgo(all: M3All) {
        upsertM3AlgoUseCase(all)
    }

    private suspend fun upsertState(all: M3All) {
        upsertM3StateUseCase(all.state)
    }

    private suspend fun upsertTasks(all: M3All) {
        val tasks = listOf(
            all.sched.task0, all.sched.task1,
            all.sched.task2, all.sched.task3,
            all.sched.task4, all.sched.task5,
            all.sched.task6, all.sched.task7,
            all.sched.task8, all.sched.task9,
        ).mapIndexed { i, task -> task.copy(i = i) }
        upsertM3TaskUseCase(tasks)
    }

    private suspend fun upsertAlarms(all: M3All) {
        val alarms = listOf(
            all.settings.alarm1, all.settings.alarm2,
            all.settings.alarm3, all.settings.alarm4,
            all.settings.alarm5, all.settings.alarm6,
            all.settings.alarm7, all.settings.alarm8,
            all.settings.alarm9
        ).mapIndexed { i, alarm -> alarm.copy(i = i + 1) }
        val minAlarms = listOf(
            all.static.settingsMin.alarm1, all.static.settingsMin.alarm2,
            all.static.settingsMin.alarm3, all.static.settingsMin.alarm4,
            all.static.settingsMin.alarm5, all.static.settingsMin.alarm6,
            all.static.settingsMin.alarm7, all.static.settingsMin.alarm8,
            all.static.settingsMin.alarm9
        )
        val maxAlarms = listOf(
            all.static.settingsMax.alarm1, all.static.settingsMax.alarm2,
            all.static.settingsMax.alarm3, all.static.settingsMax.alarm4,
            all.static.settingsMax.alarm5, all.static.settingsMax.alarm6,
            all.static.settingsMax.alarm7, all.static.settingsMax.alarm8,
            all.static.settingsMax.alarm9
        )
        upsertM3AlarmsUseCase(
            alarms = alarms,
            minAlarms = minAlarms,
            maxAlarms = maxAlarms,
        )
    }

    private suspend fun upsertStatic(all: M3All) {
        upsertM3StaticUseCase(all.static)
    }

    private suspend fun upsertSettings(all: M3All) {
        // Time
        upsertM3SettingsTimeUseCase(
            time = all.settings.time,
            timeMin = all.static.settingsMin.time,
            timeMax = all.static.settingsMax.time
        )
        // Eth
        upsertM3SettingsEthUseCase(all.settings.eth)
        // Http
        upsertM3SettingsHttpUseCase(all.settings.http)
        // Metric
        upsertM3SettingsMetricUseCase(
            metric = all.settings.metric,
            metricMin = all.static.settingsMin.metric,
            metricMax = all.static.settingsMax.metric,
        )
    }

}