package ru.cityron.domain.utils

import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.Info

fun Info.toController() = Controller(
    id = 0,
    name = "${devName.replaceFirstChar(Char::uppercaseChar)} (${name.ifEmpty { idUsr }})",
    ipAddress = ip,
    idCpu = idCpu
)