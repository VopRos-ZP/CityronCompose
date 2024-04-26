package ru.cityron.domain.model

data class EventWithDate(
    val day: String,
    val event: List<Event>
)
