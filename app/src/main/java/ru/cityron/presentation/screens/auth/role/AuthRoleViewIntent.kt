package ru.cityron.presentation.screens.auth.role

sealed interface AuthRoleViewIntent {
    data class OnPasswordChange(val value: String) : AuthRoleViewIntent
}