package ru.cityron.data.room.all.settings.eth

import ru.cityron.domain.model.Eth

fun M3SettingsEthDto.fromDto() = Eth(
    fDhcp = fDhcp,
    gw = gw,
    ip = ip,
    mac2 = mac2,
    mask = mask
)

fun Eth.toDto() = M3SettingsEthDto(
    fDhcp = fDhcp,
    gw = gw,
    ip = ip,
    mac2 = mac2,
    mask = mask
)