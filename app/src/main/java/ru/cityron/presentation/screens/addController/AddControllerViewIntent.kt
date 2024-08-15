package ru.cityron.presentation.screens.addController

sealed interface AddControllerViewIntent {
    data class Launch(val accessLevel: String) : AddControllerViewIntent
    data object OnDispose : AddControllerViewIntent
    data object OnCodeChangeFinish : AddControllerViewIntent
    data class OnCodeChange(val value: String) : AddControllerViewIntent
}