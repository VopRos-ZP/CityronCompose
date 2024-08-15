package ru.cityron.data.room.controller

import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.Status
import ru.cityron.domain.utils.fromJson
import ru.cityron.domain.utils.toJson

fun ControllerDto.toController() = Controller(
    id = id,
    name = name,
    ipAddress = ipAddress,
    idCpu = idCpu,
    idUsr = idUsr,
    status = fromJson(status)
)

fun Controller.toDto() = ControllerDto(
    id = id,
    name = name,
    ipAddress = ipAddress,
    idCpu = idCpu,
    idUsr = idUsr,
    status = toJson(status)
)
