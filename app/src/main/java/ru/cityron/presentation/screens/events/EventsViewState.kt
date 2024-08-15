package ru.cityron.presentation.screens.events

import ru.cityron.domain.model.Event
import ru.cityron.domain.utils.Filters

data class EventsViewState(
    val events: List<Event>? = null,
    val count: Int = Filters.COUNT,
    val types: Int = Filters.TYPES,
    val sources: Int = Filters.SOURCES,
    val reasons: Int = Filters.REASONS,
    val isFiltered: Boolean = false,
)
