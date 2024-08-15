package ru.cityron.domain.usecase.all.alarms

import ru.cityron.data.room.all.alarms.M3AlarmsDatabase
import ru.cityron.data.room.all.alarms.toDto
import ru.cityron.domain.model.Alarm
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertM3AlarmsUseCase @Inject constructor(
    private val m3AlarmsDatabase: M3AlarmsDatabase
) {

    suspend operator fun invoke(
        alarms: List<Alarm>,
        minAlarms: List<Alarm>,
        maxAlarms: List<Alarm>,
    ) {
        for (i in alarms.indices) {
            val alarm = alarms[i]
            val min = minAlarms[i]
            val max = maxAlarms[i]
            m3AlarmsDatabase.dao.upsertM3Alarm(
                alarm.toDto(
                    delayMin = min.delay,
                    delayMax = max.delay,
                    valueMin = min.value,
                    valueMax = max.value
                )
            )
        }
    }

}