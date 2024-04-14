package ru.cityron.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LogResult(
    val events: Events
)