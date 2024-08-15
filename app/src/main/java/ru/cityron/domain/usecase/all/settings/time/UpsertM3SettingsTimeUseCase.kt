package ru.cityron.domain.usecase.all.settings.time

import ru.cityron.data.room.all.settings.M3SettingsDatabase
import ru.cityron.data.room.all.settings.time.toDto
import ru.cityron.domain.model.Time
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertM3SettingsTimeUseCase @Inject  constructor(
    private val m3SettingsDatabase: M3SettingsDatabase
) {

    suspend operator fun invoke(
        time: Time,
        timeMin: Time,
        timeMax: Time,
    ) {
        m3SettingsDatabase.timeDao.upsertSettingsTime(time.toDto(
            zoneMin = timeMin.zone,
            zoneMax = timeMax.zone
        ))
    }

}