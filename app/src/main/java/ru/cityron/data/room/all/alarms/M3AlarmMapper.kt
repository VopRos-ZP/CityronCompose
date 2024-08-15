package ru.cityron.data.room.all.alarms

import ru.cityron.domain.model.Alarm

fun M3AlarmDto.fromDto() = Alarm(
    i = id,
    action = action,
    delay = delay,
    en = en,
    value = value,
    delayMin = delayMin,
    delayMax = delayMax,
    valueMin = valueMin,
    valueMax = valueMax,
)

fun Alarm.toDto(
    delayMin: Int = 0,
    delayMax: Int = 0,
    valueMin: Int = 0,
    valueMax: Int = 0,
) = M3AlarmDto(
    id = i,
    action = action,
    delay = delay,
    delayMin = delayMin,
    delayMax = delayMax,
    en = en,
    value = value,
    valueMin = valueMin,
    valueMax = valueMax,
)
