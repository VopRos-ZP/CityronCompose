package ru.cityron.presentation.screens.auth.role

sealed interface AuthRoleViewAction {
    data object Success : AuthRoleViewAction
}