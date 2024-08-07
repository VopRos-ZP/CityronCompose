package ru.cityron.presentation.screens.algo.fan1

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface AlgoFan1ViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : AlgoFan1ViewAction
}