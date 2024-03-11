package ru.cityron.network

import kotlinx.serialization.Serializable

@Serializable
data class LogResult(
    val events: Events
)