package ru.cityron.presentation.screens.algo.pi1

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface AlgoPi1ViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : AlgoPi1ViewAction
}