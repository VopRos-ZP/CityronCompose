package ru.cityron.data.room.controller

import ru.cityron.domain.model.Controller

fun ControllerDto.toController() = Controller(
    id = id,
    name = name,
    ipAddress = ipAddress,
    idCpu = idCpu
)

fun Controller.toDto() = ControllerDto(
    id = id,
    name = name,
    ipAddress = ipAddress,
    idCpu = idCpu
)
