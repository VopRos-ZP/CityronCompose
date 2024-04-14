package ru.cityron.core.domain.model

data class EventWithDate(
    val day: String,
    val event: List<Event>
)
