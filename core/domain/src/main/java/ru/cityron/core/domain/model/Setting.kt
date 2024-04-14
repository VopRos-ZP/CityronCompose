package ru.cityron.core.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Setting(
    @SerialName("device_id")
    val deviceId: String,
)
