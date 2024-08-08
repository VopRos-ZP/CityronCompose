package ru.cityron.presentation.screens.events

import ru.cityron.domain.model.EventWithDate

data class EventsViewState(
    val events: List<EventWithDate>? = null,
    val count: Int = 1,
    val types: Int = 0,
    val sources: Int = 0,
    val reasons: Int = 0,
    val isFiltered: Boolean = false,
)
