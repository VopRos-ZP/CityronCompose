package ru.cityron.presentation.screens.events.filters

data class EventFiltersViewState(
    val id: Int = 0,
    val countOld: Int = 1,
    val count: Int = countOld,

    val typesOld: Int = 0,
    val types: Int = typesOld,

    val sourcesOld: Int = 0,
    val sources: Int = sourcesOld,

    val reasonsOld: Int = 0,
    val reasons: Int = reasonsOld,

    val isChanged: Boolean = false,
)
