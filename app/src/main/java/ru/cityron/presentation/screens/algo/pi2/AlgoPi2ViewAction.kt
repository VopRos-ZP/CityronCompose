package ru.cityron.presentation.screens.algo.pi2

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface AlgoPi2ViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : AlgoPi2ViewAction
}