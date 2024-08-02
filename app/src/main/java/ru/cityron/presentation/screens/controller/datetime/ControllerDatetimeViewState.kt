package ru.cityron.presentation.screens.controller.datetime

data class ControllerDatetimeViewState(
    val timeFSntpOld: Int = 0,
    val timeFSntp: Int = 0,

    val timeIpOld: String = "",
    val timeIp: String = "",

    val dateOld: String = "",
    val date: String = "",

    val timeOld: String = "",
    val time: String = "",

    val timeZoneOld: String = "",
    val timeZone: String = "",

    val isChanged: Boolean = false,
)
