package ru.cityron.data.room.all.settings.time

import ru.cityron.domain.model.Time

fun M3SettingsTimeDto.fromDto() = Time(
    fSntp = fSntp,
    ip = ip,
    zone = zone,
    zoneMin = zoneMin,
    zoneMax = zoneMax
)

fun Time.toDto(
    zoneMin: Int = -14,
    zoneMax: Int = 14,
) = M3SettingsTimeDto(
    fSntp = fSntp,
    ip = ip,
    zone = zone,
    zoneMin = zoneMin,
    zoneMax = zoneMax
)
