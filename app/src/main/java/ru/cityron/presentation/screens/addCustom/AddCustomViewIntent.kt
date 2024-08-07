package ru.cityron.presentation.screens.addCustom

sealed interface AddCustomViewIntent {
    data object OnNextClick : AddCustomViewIntent
    data class OnIpChange(val value: String) : AddCustomViewIntent
}