package com.vopros.cityron.ui.screens.events

import ru.cityron.core.domain.model.EventWithDate

data class EventsViewState(
    val isLoading: Boolean = true,
    val events: List<EventWithDate> = emptyList(),
    val message: String? = null
)