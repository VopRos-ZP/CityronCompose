package ru.cityron.presentation.screens.events

sealed interface EventsViewIntent {
    data object Launch : EventsViewIntent
}