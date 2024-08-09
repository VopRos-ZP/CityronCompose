package ru.cityron.presentation.screens.addController

sealed interface AddControllerViewIntent {
    data class OnCodeChange(val value: String) : AddControllerViewIntent
}