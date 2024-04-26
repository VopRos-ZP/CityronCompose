package ru.cityron.domain.model

data class Info(
    val cmd: String,
    val type: Int,
    val idUsr: String,
    val idCpu: String,
    val devName: String,
    val name: String,
    val usePass: Int,
    val dhcp: Int,
    val ip: String,
    val mask: String,
    val setIp: String
)
