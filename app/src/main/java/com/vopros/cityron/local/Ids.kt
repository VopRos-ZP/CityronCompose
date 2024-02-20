package com.vopros.cityron.local

import com.vopros.cityron.controller.ControllerItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ids(
    @SerialName("local_token")
    val localToken: String,
    @SerialName("server_token")
    val serverToken: String,
    val controller: ControllerItem
)