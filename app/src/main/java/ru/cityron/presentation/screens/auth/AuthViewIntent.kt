package ru.cityron.presentation.screens.auth

sealed interface AuthViewIntent {
    data class Launch(val items: List<String>): AuthViewIntent
    data object OnDispose: AuthViewIntent
    data class OnItemClick(val value: String): AuthViewIntent
}