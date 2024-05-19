package ru.cityron.domain

import kotlinx.serialization.Serializable

@Serializable
data class InfoState(
    val idUsr: String,
    val idCpu: String,
    val devName: String
)
