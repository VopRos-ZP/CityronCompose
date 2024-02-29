package com.vopros.cityron.domain.events

import kotlinx.serialization.Serializable

@Serializable
data class LogResult(
    val events: Events
)