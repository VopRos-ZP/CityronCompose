package ru.cityron.presentation.screens.addController

sealed interface AddControllerViewAction {
    data object Success : AddControllerViewAction
}