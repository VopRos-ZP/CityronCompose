package ru.cityron.presentation.screens.algo.water

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface AlgoWaterViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : AlgoWaterViewAction
}