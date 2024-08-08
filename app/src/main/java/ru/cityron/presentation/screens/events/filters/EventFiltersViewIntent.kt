package ru.cityron.presentation.screens.events.filters

sealed interface EventFiltersViewIntent {
    data object Launch : EventFiltersViewIntent
    data object OnSaveClick : EventFiltersViewIntent
    data class OnCountChange(val value: Int) : EventFiltersViewIntent
    data class OnTypesChange(val value: Int) : EventFiltersViewIntent
    data class OnSourcesChange(val value: Int) : EventFiltersViewIntent
    data class OnReasonsChange(val value: Int) : EventFiltersViewIntent
}