package ru.cityron.presentation.screens.auth.role

data class AuthRoleViewState(
    val isLoading: Boolean = true,
    val password: String = "",
    val length: Int = 6,
)
