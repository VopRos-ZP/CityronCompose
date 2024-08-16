package ru.cityron.presentation.screens.auth

import ru.cityron.presentation.navigation.Screen

sealed interface AuthViewAction {
    data class OnNavigate(val screen: Screen) : AuthViewAction
}