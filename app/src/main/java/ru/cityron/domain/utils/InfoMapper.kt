package ru.cityron.domain.utils

import ru.cityron.data.room.all.Table
import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource
import ru.cityron.domain.model.Info
import ru.cityron.domain.model.Status

fun Info.toController() = Controller(
    id = Table.ID,
    name = "${devName.replaceFirstChar(Char::uppercaseChar)} (${name.ifEmpty { idUsr.uppercase() }})",
    ipAddress = ip,
    idCpu = idCpu,
    idUsr = idUsr,
    status = Status.Online(DataSource.LOCAL),
    num = "00"
)