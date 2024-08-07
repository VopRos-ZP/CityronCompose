package ru.cityron.presentation.screens.algo.timings

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface AlgoTimingsViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : AlgoTimingsViewAction
}