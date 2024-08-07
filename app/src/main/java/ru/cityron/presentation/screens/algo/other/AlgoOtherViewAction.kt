package ru.cityron.presentation.screens.algo.other

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface AlgoOtherViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : AlgoOtherViewAction
}