package ru.cityron.m3.domain.model

import kotlinx.serialization.Serializable
import ru.cityron.core.domain.model.Fw

@Serializable
data class M3Static(
    val devName: String,
    val fwBoot: Fw,
    val fwMain: Fw,
    val idCpu: String,
    val idUsr: String,
    val settingsMax: M3Settings,
    val settingsMin: M3Settings
)