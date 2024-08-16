package ru.cityron.presentation.screens.auth.role

sealed interface AuthRoleViewIntent {
    data class Launch(val accessLevel: String): AuthRoleViewIntent
    data object OnDispose: AuthRoleViewIntent
    data class OnPasswordChangeFinish(val accessLevel: String): AuthRoleViewIntent
    data class OnPasswordChange(val value: String) : AuthRoleViewIntent
}