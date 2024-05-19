package ru.cityron.data.room.ip

import ru.cityron.domain.model.Ip

fun IpDto.toIp() = Ip(
    id = id,
    address = address
)

fun Ip.toDto() = IpDto(
    id = id,
    address = address
)