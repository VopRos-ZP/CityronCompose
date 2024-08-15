package ru.cityron.data.room.all.settings.http

import ru.cityron.domain.model.Http

fun M3SettingsHttpDto.fromDto() = Http(
    fPr = fPr,
    fPu = fPu,
    fPw = fPw,
    pr = pr,
    pu = pu,
    pw = pw
)

fun Http.toDto() = M3SettingsHttpDto(
    fPr = fPr,
    fPu = fPu,
    fPw = fPw,
    pr = pr,
    pu = pu,
    pw = pw
)
