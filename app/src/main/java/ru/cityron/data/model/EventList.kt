package ru.cityron.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EventList(
    val events: Event
)
