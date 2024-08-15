package ru.cityron.data.room.all.stat

import ru.cityron.domain.model.m3.M3Static

fun M3StaticDto.fromDto() = M3Static(
    devName = devName,
    idUsr = idUsr,
    idCpu = idCpu
)

fun M3Static.toDto() = M3StaticDto(
    devName = devName,
    idUsr = idUsr,
    idCpu = idCpu
)
