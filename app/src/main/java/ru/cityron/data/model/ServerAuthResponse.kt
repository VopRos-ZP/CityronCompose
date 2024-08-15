package ru.cityron.data.model

import kotlinx.serialization.Serializable

@Serializable
class ServerAuthResponse(
    val token: String,
)