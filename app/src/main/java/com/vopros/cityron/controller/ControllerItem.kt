package com.vopros.cityron.controller

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ControllerItem(
    val name: String,
    @SerialName("ip_address")
    val ipAddress: String,
    @SerialName("id_cpu")
    val idCpu: String
)
