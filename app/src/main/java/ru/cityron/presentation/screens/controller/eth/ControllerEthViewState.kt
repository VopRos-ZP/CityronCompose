package ru.cityron.presentation.screens.controller.eth

data class ControllerEthViewState(
    val fDhcpOld: Int = 0,
    val fDhcp: Int = fDhcpOld,

    val ipOld: String = "192.168.0.127",
    val ip: String = ipOld,
    val ipIsCorrect: Boolean = true,

    val maskOld: String = "255.255.255.0",
    val mask: String = maskOld,
    val maskIsCorrect: Boolean = true,

    val gwOld: String = "192.168.0.1",
    val gw: String = gwOld,
    val gwIsCorrect: Boolean = true,

    val macOld: String = "F8:CD",
    val mac: String = macOld,
    val macIsCorrect: Boolean = true,

    val isChanged: Boolean = false
)
