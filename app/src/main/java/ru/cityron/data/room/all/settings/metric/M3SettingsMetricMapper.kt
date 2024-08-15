package ru.cityron.data.room.all.settings.metric

import ru.cityron.domain.model.Metric

fun M3SettingsMetricDto.fromDto() = Metric(
    frequency = frequency,
    valuesBits = valuesBits,
    frequencyMin = frequencyMin,
    frequencyMax = frequencyMax,
    valuesBitsMin = valuesBitsMin,
    valuesBitsMax = valuesBitsMax,
)

fun Metric.toDto(
    frequencyMin: Int = 1,
    frequencyMax: Int = 7,
    valuesBitsMin: Int = 0,
    valuesBitsMax: Int = 255,
) = M3SettingsMetricDto(
    frequency = frequency,
    valuesBits = valuesBits,
    frequencyMin = frequencyMin,
    frequencyMax = frequencyMax,
    valuesBitsMin = valuesBitsMin,
    valuesBitsMax = valuesBitsMax,
)
