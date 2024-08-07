package ru.cityron.presentation.screens.algo.fan2

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface AlgoFan2ViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : AlgoFan2ViewAction
}