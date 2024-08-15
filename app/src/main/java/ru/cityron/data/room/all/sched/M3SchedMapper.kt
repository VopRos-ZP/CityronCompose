package ru.cityron.data.room.all.sched

import ru.cityron.domain.model.m3.M3Task

fun M3SchedDto.fromDto() = M3Task(
    i = id - 1,
    day = day,
    fan = fan,
    hour = hour,
    min = min,
    mode = mode,
    on = on,
    power = power,
    temp = temp
)

fun M3Task.toDto() = M3SchedDto(
    id = i + 1,
    day = day,
    fan = fan,
    hour = hour,
    min = min,
    mode = mode,
    on = on,
    power = power,
    temp = temp
)