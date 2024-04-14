package ru.cityron.core.data.mappers

import ru.cityron.core.data.local.ControllerEntity
import ru.cityron.core.domain.model.Controller
import ru.cityron.core.domain.model.Info

fun ControllerEntity.toController() = Controller(
    id = id,
    name = name,
    ipAddress = ipAddress,
    idCpu = idCpu
)

fun Controller.toEntity() = ControllerEntity(
    id = id,
    name = name,
    ipAddress = ipAddress,
    idCpu = idCpu
)

fun Info.toController() = Controller(
    id = 0,
    name = "${devName.substring(0, 1).uppercase() + devName.substring(1).lowercase()} ${when (name) { "" -> idUsr; else -> "($name)" }}",
    ipAddress = ip,
    idCpu = idCpu
)