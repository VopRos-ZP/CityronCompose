package ru.cityron.presentation.screens.controller.datetime

data class ControllerDatetimeViewState(
    val timeFSntpOld: Int = 0,
    val timeFSntp: Int = timeFSntpOld,

    val timeIpOld: String = "46.188.16.150",
    val timeIp: String = timeIpOld,
    val timeIpIsCorrect: Boolean = true,

    val dateOld: String = "",
    val date: String = dateOld,
    val dateIsCorrect: Boolean = true,

    val timeOld: String = "",
    val time: String = timeOld,
    val timeIsCorrect: Boolean = true,

    val timeZoneOld: Int = 3,
    val timeZone: Int = timeZoneOld,
    val timeZoneRange: IntRange = (-14..14),

    val isChanged: Boolean = false,
)
