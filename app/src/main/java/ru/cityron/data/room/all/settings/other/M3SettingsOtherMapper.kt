package ru.cityron.data.room.all.settings.other

import ru.cityron.domain.model.Others

fun M3SettingsOtherDto.fromDto() = Others(
    fDebug = fDebug,
    fTest = fTest,
    loc = loc,
    ntcFilter = ntcFilter
)

fun Others.fromDto() = M3SettingsOtherDto(
    fDebug = fDebug,
    fTest = fTest,
    loc = loc,
    ntcFilter = ntcFilter
)
